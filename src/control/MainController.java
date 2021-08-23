package control;

import model.Edge;
import model.Graph;
import model.List;
import model.Vertex;

import javax.swing.tree.TreeModel;
import java.util.Stack;

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
        insertUser("S");
        insertUser("A");
        insertUser("B");
        insertUser("C");
        insertUser("D");
        insertUser("Z");
        befriend("S", "A", 10);
        befriend("S", "B", 5);
        befriend("A", "B", 2);
        befriend("A", "C", 6);
        befriend("A", "D", 3);
        befriend("B", "D", 9);
        befriend("C", "D", 2);
        befriend("C", "Z", 1);
        befriend("D", "Z", 5);


        /*
        //Freundschaftsgraph aus dem Unterricht (Tafel und Arbeitsblatt)
        insertUser("Anton");
        insertUser("Berta");
        insertUser("Charlotte");
        insertUser("Dora");
        insertUser("Emil");
        insertUser("Friedich");
        insertUser("Gustav");
        insertUser("Heinrich");
        befriend("Anton", "Emil");
        befriend("Anton", "Friedich");
        befriend("Anton", "Dora");
        befriend("Berta", "Friedich");
        befriend("Berta", "Charlotte");
        befriend("Berta", "Dora");
        befriend("Berta", "Gustav");
        befriend("Charlotte", "Friedich");
        befriend("Charlotte", "Dora");
        befriend("Dora", "Emil");
        befriend("Dora", "Gustav");
        befriend("Emil", "Heinrich");


        /*
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
         */
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
    public boolean befriend(String name01, String name02, int weight){
        //TODO 08: Freundschaften schließen.
        Vertex vertex1 = allUsers.getVertex(name01);
        Vertex vertex2 = allUsers.getVertex(name02);
        if(vertex1 != null && vertex2 != null){
            Edge edge = allUsers.getEdge(vertex1, vertex2);
            if(edge == null) {
                allUsers.addEdge(new Edge(vertex1, vertex2, weight));
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

    public String[] duerftIhrEuchEUberlegen(String name){
        //TODO 16: Schmappt sich den Teilgraphen, der mit dem angegebenem Nutzer verbunden ist.
        allUsers.setAllVertexMarks(false);
        Vertex v = allUsers.getVertex(name);
        String[] o = null;
        if(v!=null) {
            duerftIhrEuchEUberlegenRec(v);

            int counter = 0;
            List<Vertex> help = allUsers.getVertices();
            help.toFirst();
            while (help.hasAccess()){
                if(help.getContent().isMarked()){
                    counter++;
                }
                help.next();
            }

            o = new String[counter];
            help.toFirst();
            for (int i = 0; help.hasAccess(); i++){
                if(help.getContent().isMarked()){
                    o[i] = help.getContent().getID();
                }
                help.next();
            }
        }
        return o;
    }

    private void duerftIhrEuchEUberlegenRec(Vertex v){
        if(!v.isMarked()) {
            v.setMark(true);
            List<Vertex> neighbors = allUsers.getNeighbours(v);
            neighbors.toFirst();
            while (neighbors.hasAccess()) {
                duerftIhrEuchEUberlegenRec(neighbors.getContent());
                neighbors.next();
            }
        }
    }

    /**
     * Gibt eine kürzeste Verbindung zwischen zwei Personen des Sozialen Netzwerkes als String-Array zurück,
     * falls die Personen vorhanden sind und sie über eine oder mehrere Ecken miteinander verbunden sind.
     * @param name01
     * @param name02
     * @return
     */
    public String[] shortestPath(String name01, String name02){
        Vertex user01 = allUsers.getVertex(name01);
        Vertex user02 = allUsers.getVertex(name02);
        if(user01 != null && user02 != null){
            //TODO 17: Schreibe einen Algorithmus, der die kürzeste Verbindung zwischen den Nutzern name01 und name02 als String-Array zurückgibt.
            List<Vertex>[] a = breitenSuche(name01);
            boolean test = false;
            a[0].toFirst();
            while (a[0].hasAccess()){
                if(a[0].getContent() == user02){
                    test = true;
                }
                a[0].next();
            }

            if(test) {
                Stack<Vertex> path = new Stack<>();
                int counter = 0;

                Vertex target = user02;
                while (target != user01) {
                    counter++;
                    path.push(target);
                    a[0].toFirst();
                    a[1].toFirst();
                    while (a[0].hasAccess() && a[0].getContent() != target) {
                        a[0].next();
                        a[1].next();
                    }
                    target = a[1].getContent();
                }
                counter++;
                path.push(user01);

                String[] o = new String[counter];
                for (int i = 0; i < counter; i++) {
                    o[i] = path.pop().getID();
                }
                return o;
            }

        }
        return null;
    }

    public List<Vertex>[] breitenSuche(String name){
        allUsers.setAllVertexMarks(false);

        List<Vertex>[] o = new List[2];
        o[0] = new List<>();
        o[1] = new List<>();
        Vertex v = allUsers.getVertex(name);
        if(v!=null) {
            v.setMark(true);
            o[0].append(v);
            o[1].append(v);

            o[0].toFirst();
            while (o[0].hasAccess()){
                List<Vertex> help = allUsers.getNeighbours(o[0].getContent());
                help.toFirst();
                while (help.hasAccess()){
                    if(!help.getContent().isMarked()) {
                        help.getContent().setMark(true);
                        o[0].append(help.getContent());
                        o[1].append(o[0].getContent());
                    }
                    help.next();
                }
                o[0].next();
            }
        }
        return o;
    }

    public List<Vertex> findWay(Vertex start, Vertex mid, Vertex end){
        Vertex user01 = allUsers.getVertex(start.getID());
        Vertex user02 = allUsers.getVertex(mid.getID());
        Vertex user03 = allUsers.getVertex(end.getID());
        if(user01 != null && user02 != null && user03 != null){
            //TODO 17: Schreibe einen Algorithmus, der die kürzeste Verbindung zwischen den Nutzern name01 und name02 als String-Array zurückgibt.
            List<Vertex>[] a = breitenSuche(user01.getID());
            boolean test = false;
            a[0].toFirst();
            while (a[0].hasAccess()){
                if(a[0].getContent() == user02){
                    test = true;
                }
                a[0].next();
            }

            if(test) {
                Stack<Vertex> path = new Stack<>();
                int counter = 0;

                Vertex target = user02;
                while (target != user01) {
                    counter++;
                    path.push(target);
                    a[0].toFirst();
                    a[1].toFirst();
                    while (a[0].hasAccess() && a[0].getContent() != target) {
                        a[0].next();
                        a[1].next();
                    }
                    target = a[1].getContent();
                }
                counter++;
                path.push(user01);
            }

        }
        return null;
    }
}
