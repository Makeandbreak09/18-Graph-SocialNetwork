package control;

import model.Edge;
import model.Graph;
import model.List;
import model.Vertex;

/**
 * Created by Jean-Pierre on 12.01.2017.
 */
public class MainController {

    //Attribute

    //Referenzen
    private Graph allUsers;

    public MainController(){
        allUsers = new Graph();
        createSomeUsers();
    }

    /**
     * Fügt Personen dem sozialen Netzwerk hinzu.
     */
    private void createSomeUsers(){
        insertUser("1");
        insertUser("2");
        insertUser("3");
        insertUser("4");
        insertUser("5");
        insertUser("6");

        befriend("1", "2");
        befriend("1", "3");
        befriend("2", "3");
        befriend("3", "4");
        befriend("4", "5");
        befriend("4", "6");
        befriend("5", "6");
        /*
        insertUser("Ulf");
        insertUser("Silent Bob");
        insertUser("Dörte");
        insertUser("Ralle");
        befriend("Silent Bob", "Ralle");
        befriend("Dörte", "Ralle");
        befriend("Ulf", "Dörte");
         */
    }

    /**
     * Fügt einen Nutzer hinzu, falls dieser noch nicht existiert.
     * @param name
     * @return true, falls ein neuer Nutzer hinzugefügt wurde, sonst false.
     */
    public boolean insertUser(String name){
        //TODO 05: Nutzer dem sozialen Netzwerk hinzufügen.
        if(allUsers.getVertex(name) == null) {
            allUsers.addVertex(new Vertex(name));
            return true;
        }
        return false;
    }

    /**
     * Löscht einen Nutzer, falls dieser existiert. Alle Verbindungen zu anderen Nutzern werden ebenfalls gelöscht.
     * @param name
     * @return true, falls ein Nutzer gelöscht wurde, sonst false.
     */
    public boolean deleteUser(String name){
        //TODO 07: Nutzer aus dem sozialen Netzwerk entfernen.
        Vertex help = allUsers.getVertex(name);
        if(help != null) {
            allUsers.removeVertex(help);
            return true;
        }
        return false;
    }

    /**
     * Falls Nutzer vorhanden sind, so werden ihre Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     * @return
     */
    public String[] getAllUsers(){
        //TODO 06: String-Array mit allen Nutzernamen erstellen.
        List<Vertex> vertices = allUsers.getVertices();
        int a = 0;
        vertices.toFirst();
        while (vertices.hasAccess()){
            a++;
            vertices.next();
        }

        if(a>0){
            String[] o = new String[a];
            vertices.toFirst();
            for(int i = 0; i<o.length; i++){
                o[i] = vertices.getContent().getID();
                vertices.next();
            }
            return o;
        }
        return null;
    }

    /**
     * Falls der Nutzer vorhanden ist und Freunde hat, so werden deren Namen in einem String-Array gespeichert und zurückgegeben. Ansonsten wird null zurückgegeben.
     * @param name
     * @return
     */
    public String[] getAllFriendsFromUser(String name){
        //TODO 09: Freundesliste eines Nutzers als String-Array erstellen.
        Vertex v = allUsers.getVertex(name);
        if(v != null){
            List<Edge> edges = allUsers.getEdges(v);
            int a = 0;
            edges.toFirst();
            while (edges.hasAccess()){
                a++;
                edges.next();
            }

            if(a>0){
                String[] o = new String[a];
                edges.toFirst();
                for(int i = 0; i<o.length; i++){
                    String[] help = new String[2];
                    help[0] = edges.getContent().getVertices()[0].getID();
                    help[1] = edges.getContent().getVertices()[1].getID();
                    if(!help[0].equals(name)) {
                        o[i] = help[0];
                    }else{
                        o[i] = help[1];
                    }
                    edges.next();
                }
                return o;
            }
        }
        return null;
    }

    /**
     * Bestimmt den Zentralitätsgrad einer Person im sozialen Netzwerk, falls sie vorhanden ist. Sonst wird -1.0 zurückgegeben.
     * Der Zentralitätsgrad ist der Quotient aus der Anzahl der Freunde der Person und der um die Person selbst verminderten Anzahl an Nutzern im Netzwerk.
     * Gibt also den Prozentwert an Personen im sozialen Netzwerk an, mit der die Person befreundet ist.
     * @param name
     * @return
     */
    public double centralityDegreeOfUser(String name){
        //TODO 10: Prozentsatz der vorhandenen Freundschaften eines Nutzers von allen möglichen Freundschaften des Nutzers.
        List<Vertex> allV = allUsers.getVertices();
        Vertex v = allUsers.getVertex(name);
        double a = 0;
        allV.toFirst();
        while (allV.hasAccess()){
            a++;
            allV.next();
        }

        double b = 0;
        if(v != null){
            List<Edge> friends = allUsers.getEdges(v);
            friends.toFirst();
            while (friends.hasAccess()){
                b++;
                friends.next();
            }
            if(a>1) {
                return (b) / (a - 1);
            }
            return 0.0;
        }

        return -1.0;
    }

    /**
     * Zwei Nutzer des Netzwerkes gehen eine Freundschaft neu ein, falls sie sich im Netzwerk befinden und noch keine Freunde sind.
     * @param name01
     * @param name02
     * @return true, falls eine neue Freundeschaft entstanden ist, ansonsten false.
     */
    public boolean befriend(String name01, String name02){
        //TODO 08: Freundschaften schließen.
        Vertex vertex1 = allUsers.getVertex(name01);
        Vertex vertex2 = allUsers.getVertex(name02);
        if(vertex1 != null && vertex2 != null){
            Edge edge = allUsers.getEdge(vertex1, vertex2);
            if(edge == null) {
                allUsers.addEdge(new Edge(vertex1, vertex2, 1));
                return true;
            }
        }
        return false;
    }

    /**
     * Zwei Nutzer beenden ihre Freundschaft, falls sie sich im Netzwerk befinden und sie befreundet sind.
     * @param name01
     * @param name02
     * @return true, falls ihre Freundschaft beendet wurde, ansonsten false.
     */
    public boolean unfriend(String name01, String name02){
        //TODO 11: Freundschaften beenden.
        Vertex vertex1 = allUsers.getVertex(name01);
        Vertex vertex2 = allUsers.getVertex(name02);
        if(vertex1 != null && vertex2 != null){
            Edge edge = allUsers.getEdge(vertex1, vertex2);
            if(edge != null) {
                allUsers.removeEdge(edge);
                return true;
            }
        }
        return false;
    }

    /**
     * Bestimmt die Dichte des sozialen Netzwerks und gibt diese zurück.
     * Die Dichte ist der Quotient aus der Anzahl aller vorhandenen Freundschaftsbeziehungen und der Anzahl der maximal möglichen Freundschaftsbeziehungen.
     * @return
     */
    public double dense(){
        //TODO 12: Dichte berechnen.
        List<Vertex> allVertices = allUsers.getVertices();
        double a = 0;
        double b = 0;
        allVertices.toFirst();
        while (allVertices.hasAccess()){
            a++;
            String[] help = getAllFriendsFromUser(allVertices.getContent().getID());
            if(help != null) {
                b += help.length;
            }
            allVertices.next();
        }
        a--;
        for(double i = a-1; i>0; i--){
            a += i;
        }
        b = b/2;
        if(a>0) {
            return b / a;
        }
        return 0.0;
    }

    /**
     * Gibt die möglichen Verbindungen zwischen zwei Personen im sozialen Netzwerk als String-Array zurück,
     * falls die Personen vorhanden sind und sie über eine oder mehrere Ecken miteinander verbunden sind.
     * @param name01
     * @param name02
     * @return
     */
    public String[][] getLinksBetween(String name01, String name02){
        //TODO 13: Schreibe einen Algorithmus, der mindestens eine Verbindung von einem Nutzer über Zwischennutzer zu einem anderem Nutzer bestimmt. Happy Kopfzerbrechen!
        Vertex user01 = allUsers.getVertex(name01);
        Vertex user02 = allUsers.getVertex(name02);
        if(user01 != null && user02 != null){
            user01.setMark(true);
            List<List<Vertex>> allPaths = checkAllConnections(user01, user02, new List<Vertex>(), new List<List<Vertex>>());
            int a = 0;
            for(allPaths.toFirst(); allPaths.hasAccess(); allPaths.next()) {
                a++;
            }
            if(a>0) {
                String[][] s = new String[a][];
                allPaths.toFirst();
                for (int i = 0; allPaths.hasAccess(); allPaths.next(), i++) {
                    int b = 0;
                    for (allPaths.getContent().toFirst(); allPaths.getContent().hasAccess(); allPaths.getContent().next()) {
                        b++;
                    }
                    s[i] = new String[b];
                    allPaths.getContent().toFirst();
                    for (int j = 0; allPaths.getContent().hasAccess(); allPaths.getContent().next(), j++) {
                        s[i][j] = allPaths.getContent().getContent().getID();
                    }
                }
                return s;
            }
        }

        return null;
    }

    public List<List<Vertex>> checkAllConnections(Vertex v1, Vertex v2, List<Vertex> pPath, List<List<Vertex>> pAllPaths){
        v1.setMark(true);
        List<Vertex> connections = allUsers.getNeighbours(v1);
        connections.toFirst();
        while (connections.hasAccess()){
            List<Vertex> path = new List<Vertex>();
            pPath.toFirst();
            while (pPath.hasAccess()){
                path.append(pPath.getContent());
                pPath.next();
            }
            if(connections.getContent() == v2){
                pAllPaths.append(path);
            }else if(!connections.getContent().isMarked()){
                path.append(connections.getContent());
                checkAllConnections(connections.getContent(), v2, path, pAllPaths);
            }
            connections.next();
        }
        v1.setMark(false);

        return pAllPaths;
    }

    /**
     * Gibt zurück, ob es sich bei allUsers um einen zusammenhängenden Graphen handelt, also kein Knoten ohne Nachbarn ist.
     * @return true, falls zusammenhängend, sonst false.
     */
    public boolean testIfConnectedEasy(){
        //TODO 14: Schreibe einen Algorithmus, der explizit den von uns benutzten Aufbau der Datenstruktur Graph und ihre angebotenen Methoden so ausnutzt, dass schnell (!) iterativ geprüft werden kann, ob der Graph allUsers zusammenhängend ist. Dies lässt sich mit einer einzigen Schleife prüfen.
        List<Vertex> help = allUsers.getVertices();
        for(help.toFirst(); help.hasAccess(); help.next()){
            if(allUsers.getNeighbours(help.getContent()).isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * Gibt zurück, ob vom ersten Knoten in der Liste aller Knoten ausgehend alle anderen Knoten erreicht, also markiert werden können.
     * Nach der Prüfung werden noch vor der Rückgabe alle Knoten demarkiert.
     * @return true, falls alle Knoten vom ersten ausgehend markiert wurden, sonst false.
     */
    public boolean testIfConnectedTough(){
        //TODO 15: Schreibe einen Algorithmus, der ausgehend vom ersten Knoten in der Liste aller Knoten versucht, alle anderen Knoten über Kanten zu erreichen und zu markieren.
        List<Vertex> helpList = allUsers.getVertices();
        helpList.toFirst();
        Vertex firstUser = null;
        if(helpList.hasAccess()) {
            firstUser = helpList.getContent();
        }
        for(helpList.next(); helpList.hasAccess(); helpList.next()){
            if(getLinksBetween(firstUser.getID(), helpList.getContent().getID()) == null){
                return false;
            }
        }
        return true;
    }
<<<<<<< HEAD

<<<<<<< HEAD
    public String[] breitenSuche(String name){
=======
    public String[] duerftIhrEuchEUberlegen(String name){
        //TODO 16:
>>>>>>> parent of 4b2c42d (Update MainController.java)
        allUsers.setAllVertexMarks(false);

        String[] o = null;
        Vertex v = allUsers.getVertex(name);
        if(v!=null) {
            List<Vertex> help = new List<>();
            v.setMark(true);
            help.append(v);
            help.concat(allUsers.getNeighbours(v));

            help.toFirst();
            while (help.hasAccess()){
                help.getContent().setMark(true);
                help.next();
            }

            help.toFirst();
            while (help.hasAccess()){
                List<Vertex> help2 = allUsers.getNeighbours(help.getContent());
                help2.toFirst();
                while (help2.hasAccess()){
                    if(!help2.getContent().isMarked()) {
                        help2.getContent().setMark(true);
                        help.append(help2.getContent());
                    }
                    help2.next();
                }
                help.next();
            }

            int a = 0;
            help.toFirst();
            while (help.hasAccess()){
                a++;
                help.next();
            }

            o = new String[a];
            help.toFirst();
            for (int i = 0; help.hasAccess(); i++){
                o[i] = help.getContent().getID();
                help.next();
            }
        }
        return o;
    }
=======
>>>>>>> parent of 43b0f6a (getConnectedVertices)
}
