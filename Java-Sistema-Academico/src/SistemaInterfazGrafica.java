import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class SistemaInterfazGrafica extends JFrame {

    private JTable table;
    private JTextArea reporteArea;
    private JTextArea tendenciasArea;

    public SistemaInterfazGrafica(int n) {

        setTitle("Sistema de Calificaciones -|Exelencia Academica|-");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTabla = new JPanel(new BorderLayout());
        String[] columnNames = {"Nombre", "Matematicas", "Fisica", "Quimica", "Promedio", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPaneTabla = new JScrollPane(table);
        panelTabla.add(scrollPaneTabla, BorderLayout.CENTER);
        add(panelTabla, BorderLayout.NORTH);

        JPanel panelReporte = new JPanel(new GridLayout(1, 2));
        reporteArea = new JTextArea();
        reporteArea.setEditable(false);
        JScrollPane scrollPaneReporte = new JScrollPane(reporteArea);
        panelReporte.add(scrollPaneReporte);

        tendenciasArea = new JTextArea();
        tendenciasArea.setEditable(false);
        JScrollPane scrollPaneTendencias = new JScrollPane(tendenciasArea);
        panelReporte.add(scrollPaneTendencias);

        add(panelReporte, BorderLayout.CENTER);

        Random random = new Random();
        String[] nombresGenerados = {
            "Carlos", "Daniel", "Robert", "Juan", "Luis", "Maria", "Alberto", "Sofia", "Pedro", "Camila",
            "Diego", "Daniela", "Emily", "Lucia", "Miguel"
        };
        String[] nombres = new String[n];
        double[][] calificaciones = new double[n][3];

        for (int i = 0; i < n; i++) {
            nombres[i] = nombresGenerados[random.nextInt(nombresGenerados.length)];
            for (int j = 0; j < 3; j++) {
                calificaciones[i][j] = Math.round(random.nextDouble() * 10 * 100) / 100.0;
            }
        }

        Map<Double, Integer> frecuenciaCalificaciones = new HashMap<>();
        for (int i = 0; i < nombres.length; i++) {
            double promedio = calcularPromedio(calificaciones, i);
            String estado = (promedio >= 7) ? "Aprobado" : "Reprobado";
            model.addRow(new Object[]{
                nombres[i],
                calificaciones[i][0],
                calificaciones[i][1],
                calificaciones[i][2],
                promedio,
                estado
            });

            reporteArea.append("Estudiante: " + nombres[i] + "\n");
            reporteArea.append("Calificaciones: " + calificaciones[i][0] + ", " + calificaciones[i][1] + ", " + calificaciones[i][2] + "\n");
            reporteArea.append("Promedio: " + promedio + " - " + estado + "\n\n");

            // Contar la frecuencia de las calificaciones
            for (double calificacion : calificaciones[i]) {
                frecuenciaCalificaciones.put(calificacion, frecuenciaCalificaciones.getOrDefault(calificacion, 0) + 1);
            }
        }

        tendenciasArea.append("Tendencias de calificaciones:\n");
        for (Map.Entry<Double, Integer> entry : frecuenciaCalificaciones.entrySet()) {
            tendenciasArea.append("Calificación: " + entry.getKey() + " - Frecuencia: " + entry.getValue() + "\n");
        }
    }

    public static double calcularPromedio(double[][] calificaciones, int cont) {
        double suma = 0;
        for (double calificacion : calificaciones[cont]) {
            suma += calificacion;
        }
        return Math.round((suma / calificaciones[cont].length) * 100) / 100.0;
    }

    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Ingrese el número de estudiantes del paralelo:");
        int n = Integer.parseInt(input);

        SwingUtilities.invokeLater(() -> {
            SistemaInterfazGrafica interfaz = new SistemaInterfazGrafica(n);
            interfaz.setVisible(true);
        });
    }
}
