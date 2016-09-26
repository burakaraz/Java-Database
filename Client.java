
import com.mysql.jdbc.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yamac
 */
public class Client {

    protected Random rng;
    static protected MessageDigest md;
    static public ISongInfoService stub;

    public static void main(String[] args) throws Exception {

        Client client = new Client();

        //Code logic goes here
        //get input
        //process command
        //************************************************
        while(true)
        {
            System.out.println("when you enter the 'get <filename>' and if the file hash is not in the database, first it is added to the db, then read into a serialize file.");
            System.out.println("If you want to exit, enter 'exit'.");
            System.out.println("Input format must be in 'get <filename>' 'update <filename>' 'read <filename> | *'");
            System.out.println("Please enter input:");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            String[] c = input.split(" ");
            String command = c[0];
            

            if (command.equals("get")) {
                String fileName = c[1];
                File file = new File(fileName + ".audio");
                FileInputStream fileInputStream = null;
                //get input.audio: 
                //read the input file. 
                byte[] inputContent = new byte[(int) file.length()];
                try {
                    fileInputStream = new FileInputStream(file);
                    fileInputStream.read(inputContent);
                    fileInputStream.close();
                } catch (Exception e) {
                    System.out.println("There is no file in inputs folder with the same name with input.");
                    continue;
                }

                //calculate hash
                byte[] digest = md.digest(inputContent);
                BigInteger bi = new BigInteger(1, digest);
                String hash = bi.toString(16);

                SongInfo si = new SongInfo();
                si.hash = hash;
                si = stub.getSong(si);

                //System.out.println(si.name);
                //create new song
                Song s = new Song(si, inputContent);

                System.out.println("Please enter new output name:");
                Scanner newFileName = new Scanner(System.in);
                String newFile = newFileName.nextLine();
                SerializeSong(s, newFile);

            } else if (command.equals("read")) {
                String fileName = c[1];
                if (fileName.equals("*")) {
                    File dir = new File(System.getProperty("user.dir"));
                    File[] list = dir.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            // get last index for '.' char
                            int lastIndex = name.lastIndexOf('.');
                            // get extension
                            if (lastIndex > 0) {
                                String str = name.substring(lastIndex);
                                // match extension
                                if (str.equals(".ser")) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                    for (int i = 0; i < list.length; i++) {
                        String pathField = list[i].getName();
                        String[] path = pathField.split("\\.");

                        Song s = UnSerializeSong(path[0]);
                        printSong(s);
                    }
                } else {
                    Song s = UnSerializeSong(fileName);
                    if(s == null)
                    {
                        continue;
                    }
                    printSong(s);
                }
            } else if(command.equals("update")){
                String fileName = c[1];
                Song s = UnSerializeSong(fileName);
                if(s == null)
                {
                    continue;
                }
                // take input
                System.out.println("Enter the name: (If you dont want to change, just press enter)");
                Scanner nameField = new Scanner(System.in);
                String name = nameField.nextLine();

                System.out.println("Enter the artist: (If you dont want to change, just press enter)");
                Scanner artistField = new Scanner(System.in);
                String artist = artistField.nextLine();

                System.out.println("Enter the album: (If you dont want to change, just press enter)");
                Scanner albumField = new Scanner(System.in);
                String album = albumField.nextLine();

                System.out.println("Enter the genre: (If you dont want to change, just press enter)");
                Scanner genreField = new Scanner(System.in);
                String genre = genreField.nextLine();

                System.out.println("Enter the year: (If you dont want to change, just press enter)");
                Scanner yearField = new Scanner(System.in);
                String year = yearField.nextLine();

                //System.out.println(s.info.name);
                if (!name.isEmpty()) {
                    s.info.name = name;
                }
                if (!artist.isEmpty()) {
                    s.info.artist = artist;
                }
                if (!album.isEmpty()) {
                    s.info.album = album;
                }
                if (!genre.isEmpty()) {
                    s.info.genre = genre;
                }
                if (!year.isEmpty()) {
                    int yearInt = Integer.parseInt(year);
                    s.info.year = yearInt;
                }

                int flag = stub.updateSong(s.info);
                if(flag == 1)
                {
                    SerializeSong(s, fileName);
                }
                //System.out.println(name + " " + artist + " " + album + " " + genre + " " + year);

            }
            else if(command.equals("exit"))
            {
                break;
            }
        }

    }

    public static Song UnSerializeSong(String fileName) {
        Song s = null;
        try {
            FileInputStream fis = new FileInputStream(fileName + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s = (Song) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("System can not find the file.");
        }
        return s;
        //printSong(s);
    }

    public static void printSong(Song s) {
        System.out.println("Song Informations ");
        System.out.println("Song id: " + s.info.id);
        System.out.println("Song Name: " + s.info.name);
        System.out.println("Song Artist: " + s.info.artist);
        System.out.println("Song Album:" + s.info.album);
        System.out.println("Song Genre:" + s.info.genre);
        System.out.println("Song Year: " + s.info.year);
        System.out.println("Song Hash:" + s.info.hash);
        System.out.println();
    }

    public static void SerializeSong(Song s, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
            oos.close();
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    public Client() throws Exception {
        rng = new Random();
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        //connect to RMI repository. Pay attention to the bound service name in the server.
        //(do not forget to start the repository service)

        String host = "localhost";
        String name = "RMIServer";

        Registry registry;
        registry = LocateRegistry.getRegistry(host);
        stub = (ISongInfoService) registry.lookup(name);
    }

}
