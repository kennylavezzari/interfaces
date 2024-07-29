import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TriviaServer extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea chatArea;
    private ServerSocket serverSocket;
    private List<Socket> clients;
    private List<String> playerNames;
    private HashMap<String, Integer> playerScores;

    public TriviaServer() {
        setTitle("Trivia Game Server");
        chatArea = new JTextArea(20, 40);
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        clients = new ArrayList<>();
        playerNames = new ArrayList<>();
        playerScores = new HashMap<>();

        try {
            serverSocket = new ServerSocket(12345);
            chatArea.append("Esperando conexión de jugadores...\n");

            while (clients.size() < 2) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String playerName = in.readLine();

                chatArea.append("Nuevo jugador conectado:  " + playerName);
                playerNames.add(playerName);
                playerScores.put(playerName, 0);
            }

            chatArea.append("Todos los jugadores conectados. Comenzando juego...\n");

            List<String> questions = new ArrayList<>();
            questions.add("¿a1: Por qué se extinguieron los dinosaurios?  a)meteorito  b)clima  c)lluvia  d)edad de hielo");
            questions.add("¿a2: Los dinousaurios eran omnívoros?  a)verdadero  b)falso");
            questions.add("¿a3: Cuáles son los parientes de los dinousaurios?  a)mamíferos  b)reptiles");
            questions.add("¿a4: Los dinosaurios eran?  a)invertebrados  b)vertebrados");
            questions.add("¿a5: Cuál de ellos es un dinosaurio?  a)oso  b)perro  c)Braquiosaurio");

            Collections.shuffle(questions);

            for (int i = 0; i < 5; i++) {
                String question = questions.get(i);
                chatArea.append("Pregunta " + (i + 1) + ": " + question + "\n");

                for (Socket client : clients) {
                    PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);
                    BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    clientOut.println(question);
                    String answer = "";
                    answer = clientIn.readLine();
                    chatArea.append(playerNames.get(clients.indexOf(client)) + ": " + answer + "\n");
                    
                    if (question.substring(0, 3).equals("¿a1") && answer.equals("a")) {
                        playerScores.put(playerNames.get(clients.indexOf(client)), playerScores.get(playerNames.get(clients.indexOf(client))) + 1);
                    }

                    if (question.substring(0, 3).equals("¿a2") && answer.equals("a")) {
                        playerScores.put(playerNames.get(clients.indexOf(client)), playerScores.get(playerNames.get(clients.indexOf(client))) + 1);
                    }

                    if (question.substring(0, 3).equals("¿a3") && answer.equals("b")) {
                        playerScores.put(playerNames.get(clients.indexOf(client)), playerScores.get(playerNames.get(clients.indexOf(client))) + 1);
                    }

                    if (question.substring(0, 3).equals("¿a4") && answer.equals("b")) {
                        playerScores.put(playerNames.get(clients.indexOf(client)), playerScores.get(playerNames.get(clients.indexOf(client))) + 1);
                    }

                    if (question.substring(0, 3).equals("¿a5") && answer.equals("c")) {
                        playerScores.put(playerNames.get(clients.indexOf(client)), playerScores.get(playerNames.get(clients.indexOf(client))) + 1);
                    }
                    
                    
                    //playerScores.put(playerNames.get(clients.indexOf(client)), playerScores.get(playerNames.get(clients.indexOf(client))) + 1);
                }
            }

            chatArea.append("Juego completado. Guardando progreso...\n");

            // Guardar el progreso del jugador
            for (String playerName : playerScores.keySet()) {
                chatArea.append(playerName + ": " + playerScores.get(playerName) + " puntos\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                for (Socket client : clients) {
                    client.close();
                }
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new TriviaServer();
    }
}
