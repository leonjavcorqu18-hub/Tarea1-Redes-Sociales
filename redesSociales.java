import java.io.*;
import java.util.*;

public class redesSociales {

    static class Registro {
        String redSocial;
        String concepto;
        int año;
        Map<String, String> meses = new HashMap<>();
    }

    public static List<Registro> leerArchivo(String archivo) throws IOException {
        List<Registro> registros = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String encabezado = br.readLine();
        if (encabezado == null) {
            br.close();
            return registros;
        }
        String[] columnas = encabezado.split(",");
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            
            // Parsing más robusto para manejar números con comas
            List<String> campos = new ArrayList<>();
            StringBuilder campoActual = new StringBuilder();
            boolean dentroDeComillas = false;
            
            for (int i = 0; i < linea.length(); i++) {
                char c = linea.charAt(i);
                if (c == '"') {
                    dentroDeComillas = !dentroDeComillas;
                } else if (c == ',' && !dentroDeComillas) {
                    campos.add(campoActual.toString().trim());
                    campoActual = new StringBuilder();
                } else {
                    campoActual.append(c);
                }
            }
            campos.add(campoActual.toString().trim()); // Agregar el último campo
            
            if (campos.size() < 3) continue; // Necesitamos al menos red, concepto, año
            
            Registro r = new Registro();
            r.redSocial = campos.get(0);
            r.concepto = campos.get(1);
            r.año = Integer.parseInt(campos.get(2));
            
            // Procesar los meses
            for (int i = 3; i < Math.min(campos.size(), columnas.length); i++) {
                String valor = (!campos.get(i).isEmpty()) ? campos.get(i) : "0";
                r.meses.put(columnas[i].trim().toUpperCase(), valor);
            }
            registros.add(r);
        }
        br.close();
        return registros;
    }

    // Diferencia de seguidores en Twitter entre enero y junio
    public static void diferenciaSeguidoresTwitter(List<Registro> registros) {
        for (Registro r : registros) {
            // Corregido: coincidencia exacta con el CSV
            if (r.redSocial.equalsIgnoreCase("TWITTER") && r.concepto.equalsIgnoreCase("SEGUIDORES (FOLLOWERS)")) {
                double enero = parseNumero(r.meses.getOrDefault("ENERO", "0"));
                double junio = parseNumero(r.meses.getOrDefault("JUNIO", "0"));
                System.out.println("\n 1) Diferencia de seguidores en Twitter entre enero y junio: " + (junio - enero));
                return;
            }
        }
        System.out.println("\n► No se encontró la fila de seguidores de Twitter en el archivo.");
    }

    // Diferencia de visualizaciones de YouTube entre dos meses seleccionados
    public static void diferenciaVisualizacionesYoutube(List<Registro> registros, String mes1, String mes2) {
        for (Registro r : registros) {
            if (r.redSocial.equalsIgnoreCase("YOUTUBE") && r.concepto.equalsIgnoreCase("VISUALIZACIONES")) {
                double v1 = parseNumero(r.meses.getOrDefault(mes1.toUpperCase(), "0"));
                double v2 = parseNumero(r.meses.getOrDefault(mes2.toUpperCase(), "0"));
                System.out.println("\n 2) Diferencia de visualizaciones de YouTube entre " + mes1 + " y " + mes2 + ": " + (v2 - v1));
                return;
            }
        }
        System.out.println("\n► No se encontró la fila de visualizaciones de YouTube en el archivo.");
    }

    // Promedio de crecimiento mensual de TWITTER
    public static void promedioCrecimientoTwitter(List<Registro> registros) {
        for (Registro r : registros) {
            if (r.redSocial.equalsIgnoreCase("TWITTER") && r.concepto.equalsIgnoreCase("CRECIMIENTO DE FOLLOWERS")) {
                String[] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO"};
                double suma = 0;
                int cuenta = 0;
                for (String mes : meses) {
                    double valor = parseNumero(r.meses.getOrDefault(mes, "0"));
                    if (valor != 0) { // Solo contar valores no cero
                        suma += valor;
                        cuenta++;
                    }
                }
                double promedio = cuenta == 0 ? 0 : suma / cuenta;
                System.out.println("\n 3) Promedio de crecimiento mensual de TWITTER (enero-junio): " + promedio);
                return;
            }
        }
        System.out.println("\n► No se encontró la fila de crecimiento de Twitter en el archivo.");
    }

    // Promedio de crecimiento mensual de FACEBOOK
    public static void promedioCrecimientoFacebook(List<Registro> registros) {
        for (Registro r : registros) {
            if (r.redSocial.equalsIgnoreCase("FACEBOOK") && r.concepto.equalsIgnoreCase("CRECIMIENTO (seguidores)")) {
                String[] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO"};
                double suma = 0;
                int cuenta = 0;
                for (String mes : meses) {
                    double valor = parseNumero(r.meses.getOrDefault(mes, "0"));
                    if (valor != 0) { // Solo contar valores no cero
                        suma += valor;
                        cuenta++;
                    }
                }
                double promedio = cuenta == 0 ? 0 : suma / cuenta;
                System.out.println("\n 4) Promedio de crecimiento mensual de FACEBOOK (enero-junio): " + promedio);
                return;
            }
        }
        System.out.println("\n► No se encontró la fila de crecimiento de Facebook en el archivo.");
    }

    // Promedio de "Me gusta" de YouTube, Twitter y Facebook (enero a junio)
    public static void promedioMeGusta(List<Registro> registros) {
        String[] meses = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO"};
        double total = 0;
        int count = 0;
        
        for (Registro r : registros) {
            // Buscar registros que contengan "ME GUSTA" en el concepto
            if ((r.redSocial.equalsIgnoreCase("YOUTUBE") && r.concepto.equalsIgnoreCase("ME GUSTA")) ||
                (r.redSocial.equalsIgnoreCase("TWITTER") && r.concepto.equalsIgnoreCase("ME GUSTA")) ||
                (r.redSocial.equalsIgnoreCase("FACEBOOK") && r.concepto.equalsIgnoreCase("ME GUSTA"))) {
                
                for (String mes : meses) {
                    double valor = parseNumero(r.meses.getOrDefault(mes, "0"));
                    total += valor;
                    count++;
                }
            }
        }
        double promedio = count == 0 ? 0 : total / count;
        System.out.println("\n 5) Promedio de 'Me gusta' entre YouTube, Twitter y Facebook (enero-junio): " + promedio);
    }

    // Método corregido para parsing de números
    public static double parseNumero(String valor) {
        if (valor == null || valor.trim().isEmpty()) return 0;
        
        // Remover comas como separadores de miles y espacios
        valor = valor.trim().replace(",", "");
        
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        String archivo = "datos_redes_sociales.csv";
        List<Registro> registros = leerArchivo(archivo);

        // 1. Diferencia de seguidores en Twitter entre enero y junio
        diferenciaSeguidoresTwitter(registros);

        // 2. Diferencia de visualizaciones de YouTube entre dos meses (ingresados por teclado)
        Scanner sc = new Scanner(System.in);
        System.out.print("\nIngrese un mes (enero-junio): ");
        String mes1 = sc.nextLine();
        System.out.print("Ingrese el segundo mes (enero-junio): ");
        String mes2 = sc.nextLine();
        diferenciaVisualizacionesYoutube(registros, mes1, mes2);

        // 3. Promedio de crecimiento mensual de Twitter y Facebook entre enero y junio
        promedioCrecimientoTwitter(registros);
        promedioCrecimientoFacebook(registros);

        // 4. Promedio de "Me gusta" en YouTube, Twitter y Facebook entre enero y junio
        promedioMeGusta(registros);
        
        sc.close();
    }
}