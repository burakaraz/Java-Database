
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements ISongInfoService {

    private Random rng;
    private String name;
    private String driver;
    private String url;
    private String database;
    private String username;
    private String password;

    private Connection connection = null;
    private Statement stmt = null;

    static int flag = -1; 
    
    public final void initialize() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("" + cnfe);
        }

        setUrl("jdbc:mysql://localhost:3306/");
        setDatabase("peertopeer");
        setUsername("root");
        setPassword("root1234");

        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(getUrl() + getDatabase(), getUsername(), getPassword());
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from songs");
            /*while(rs.next())
             {
             int val = rs.getInt("idsongs");
             System.out.println(val);
             }*/

        } catch (SQLException e) {
            System.out.println("" + e);
        }
        this.setName("songs");

    }

    public Server() {
        super();
        rng = new Random();
        initialize();
        //more initialization stuff
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("java.rmi.server.hostname", "localhost");
        System.setProperty("java.rmi.server.codebase", "file:./");

        Server server = new Server();
        //create stub and bind it to the name in the registry
        String name = "RMIServer";
        ISongInfoService stub = null;
        stub = (ISongInfoService) UnicastRemoteObject.exportObject(server, 0);
        LocateRegistry.createRegistry(1099);
        LocateRegistry.getRegistry().rebind(name, stub);
        System.out.println("Server ready");

    }

    @Override
    public int updateSong(SongInfo arg) throws RemoteException {

        String hash = arg.hash;
        try {
            PreparedStatement update = connection.prepareStatement("UPDATE songs SET name = ?, artist = ?, album = ?, genre = ?,year = ? WHERE hash = ?");
            update.setString(1, arg.name);
            update.setString(2, arg.artist);
            update.setString(3, arg.album);
            update.setString(4, arg.genre);
            update.setInt(5, arg.year);
            update.setString(6, arg.hash);
            flag = update.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    @Override
    public SongInfo getSong(SongInfo arg) throws RemoteException {
        SongInfo si = new SongInfo();
        String hash = arg.hash;
        try {
            ResultSet rs = stmt.executeQuery("select * from songs where hash='" + hash + "'");         
            if (rs.next()) {
                si.id = rs.getInt("idsongs");
                si.name = rs.getString("name");
                si.artist = rs.getString("artist");
                si.album = rs.getString("album");
                si.genre = rs.getString("genre");
                si.year = rs.getInt("year");
                si.hash = rs.getString("hash");
            }
            else
            {
                addSong(arg);
                ResultSet rs2 = stmt.executeQuery("select * from songs where hash='" + hash + "'");
                if (rs2.next()) {
                    si.id = rs2.getInt("idsongs");
                    si.name = rs2.getString("name");
                    si.artist = rs2.getString("artist");
                    si.album = rs2.getString("album");
                    si.genre = rs2.getString("genre");
                    si.year = rs2.getInt("year");
                    si.hash = rs2.getString("hash");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return si;
    }
    
    public void addSong(SongInfo si)
    {
        try {
            PreparedStatement insert = connection.prepareStatement("insert into songs(hash) values(?)");
            insert.setString(1, si.hash);
            int flag = insert.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the rng
     */
    public Random getRng() {
        return rng;
    }

    /**
     * @param rng the rng to set
     */
    public void setRng(Random rng) {
        this.rng = rng;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * **************************************
     *										*
     * implement the interface methods	* *
     * **************************************
     */
}

            //setRng          (new Random ())                  ;

