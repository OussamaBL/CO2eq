    package config;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;

    public class connexion {
        private String Pilote;
        private String URL ;
        private String Login ;
        private String Password ;
        private Connection MyConnexion ;

        public connexion(String Pilote,String URL, String login, String password) {
            this.URL = URL;
            this.Login = login;
            this.Password = password;
            this.Pilote=Pilote;
        }

        public String getPilote() {
            return Pilote;
        }

        public void setPilote(String pilote) {
            Pilote = pilote;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public String getLogin() {
            return Login;
        }

        public void setLogin(String login) {
            Login = login;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public Connection getMyConnexion() {
            return MyConnexion;
        }

        public void setMyConnexion(Connection myConnexion) {
            MyConnexion = myConnexion;
        }
        public connexion(){}

        public void SeConnecter(){
            try{
                Class.forName(this.Pilote);
                System.out.println(" Chargement de Pilote OK ....");
                this.MyConnexion = DriverManager.getConnection(URL, Login, Password);
                System.out.println("Connexion établie : Go ...");
            }
            catch (ClassNotFoundException ex)
            {
                System.err.println(" Problème de chargement du Pilote !!!!");
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
