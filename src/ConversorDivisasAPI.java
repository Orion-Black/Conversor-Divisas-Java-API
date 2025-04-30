import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import org.json.JSONObject;

public class ConversorMoneda extends JFrame {
    private JComboBox<String> monedaDestino;
    private JTextField campoMonto;
    private JLabel resultado;

    public ConversorMoneda() {
        setTitle("Conversor de Moneda");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] monedas = {"USD - Dólar", "EUR - Euro", "JPY - Yen"};
        monedaDestino = new JComboBox<>(monedas);
        campoMonto = new JTextField(10);
        resultado = new JLabel("Resultado: $0.00");

        campoMonto.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                convertir();
            }
        });

        monedaDestino.addItemListener(e -> convertir());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Monto en MXN:"));
        panel.add(campoMonto);
        panel.add(new JLabel("Convertir a:"));
        panel.add(monedaDestino);
        panel.add(resultado);

        add(panel);
    }

    private void convertir() {
        try {
            double monto = Double.parseDouble(campoMonto.getText());
            int index = monedaDestino.getSelectedIndex();
            String simbolo = "";
            double tasa = obtenerTasaCambio(index);

            switch (index) {
                case 0 -> simbolo = "$ ";
                case 1 -> simbolo = "€ ";
                case 2 -> simbolo = "¥ ";
            }

            double resultadoFinal = monto * tasa;
            resultado.setText("Resultado: " + simbolo + String.format("%.2f", resultadoFinal));
        } catch (NumberFormatException e) {
            resultado.setText("Resultado: --");
        }
    }

    private double obtenerTasaCambio(int tipo) {
        try {
            if (tipo == 0) { // MXN to USD
                JSONObject json = fetchApi("USD-MXN");
                return 1.0 / json.getJSONObject("USDMXN").getDouble("bid");
            } else if (tipo == 1) { // MXN to EUR
                JSONObject json = fetchApi("EUR-MXN");
                return 1.0 / json.getJSONObject("EURMXN").getDouble("bid");
            } else if (tipo == 2) {
                // MXN -> USD -> JPY = (1/MXNUSD) * USDJPY
                JSONObject mxn_usd = fetchApi("USD-MXN");
                JSONObject usd_jpy = fetchApi("JPY-USD");
                double tasa_mxn_usd = 1.0 / mxn_usd.getJSONObject("USDMXN").getDouble("bid");
                double tasa_usd_jpy = 1.0 / usd_jpy.getJSONObject("JPYUSD").getDouble("bid");
                return tasa_mxn_usd * tasa_usd_jpy;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener la tasa de cambio: " + e.getMessage());
        }
        return 0;
    }

    private JSONObject fetchApi(String pair) throws Exception {
        URL url = new URL("https://economia.awesomeapi.com.br/last/" + pair);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();
        return new JSONObject(content.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConversorMoneda().setVisible(true));
    }
}

