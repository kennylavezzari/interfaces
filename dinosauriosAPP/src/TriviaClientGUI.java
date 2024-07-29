import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class TriviaClientGUI {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String playerName = "";

            // Leer nombre del jugador
            if(playerName == "") {

                playerName = JOptionPane.showInputDialog(null, "Ingrese su nombre:");
            }

            // Enviar nombre al servidor
            out.println(playerName);

            String response;
            while ((response = in.readLine()) != null) {
                // Verificar si el jugador tiene un progreso guardado
                if (response.startsWith("PROGRESO:")) {
                    int progresoGuardado = Integer.parseInt(response.substring(10));
                    JOptionPane.showMessageDialog(null, "¡Bienvenido de nuevo, " + playerName + "!\nTu progreso guardado es: " + progresoGuardado + " puntos");
                } else {
                    // Mostrar pregunta y recibir respuesta
                    String question = response;
                    String answer = JOptionPane.showInputDialog(null, playerName + "\n " + question);

                    // Enviar respuesta al servidor
                    out.println(answer);
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
