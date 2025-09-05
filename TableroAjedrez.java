public class TableroAjedrez {
    public static void main(String[] args) {
        Array2D<String> tablero = new Array2D<>(8, 8);
        tablero.limpiar("   ");
        
        // Como no pude colocar los íconos, les damos un valor para diferenciar piezas negras y blancas
        String rB = " R ", nB = " N ", bB = " B ", qB = " Q ", kB = " K ", pB = " P ";
        String rW = " r ", nW = " n ", bW = " b ", qW = " q ", kW = " k ", pW = " p ";
        
        // Figuras negras
        tablero.establecerElemento(0,0,rB); tablero.establecerElemento(0,1,nB);
        tablero.establecerElemento(0,2,bB); tablero.establecerElemento(0,3,qB);
        tablero.establecerElemento(0,4,kB); tablero.establecerElemento(0,5,bB);
        tablero.establecerElemento(0,6,nB); tablero.establecerElemento(0,7,rB);
        
        // Peones negros
        for(int c=0; c<8; c++) {
            tablero.establecerElemento(1,c,pB);
        }
        
        // Figuras blancas
        tablero.establecerElemento(7,0,rW); tablero.establecerElemento(7,1,nW);
        tablero.establecerElemento(7,2,bW); tablero.establecerElemento(7,3,qW);
        tablero.establecerElemento(7,4,kW); tablero.establecerElemento(7,5,bW);
        tablero.establecerElemento(7,6,nW); tablero.establecerElemento(7,7,rW);
        
        // Peones blancos
        for(int c=0; c<8; c++) {
            tablero.establecerElemento(6,c,pW);
        }
        
        System.out.println("- T A B L E R O  D E  A J E D R E Z -");
        tablero.mostrarTablero();
        System.out.println("significados:");
        System.out.println("piezas negras: R=torre, N=caballo, B=alfil, Q=reina, K=rey, P=peones");
        System.out.println("piezas blancas: r=torre, n=caballo, b=alfil, q=reina, k=rey, p=peones");
        System.out.println("=======================================");
    }
}

class Array2D<T> {
    private final int renglones;
    private final int columnas;
    private final Object[][] datos;

    public Array2D(int ren, int col) {
        this.renglones = ren;
        this.columnas = col;
        this.datos = new Object[ren][col];
    }

    public void limpiar(T dato) {
        for(int r=0; r<renglones; r++) {
            for(int c=0; c<columnas; c++) {
                datos[r][c] = dato;
            }
        }
    }

    public int getNumRenglones() { 
        return renglones; 
    }
    
    public int getNumColumnas() { 
        return columnas; 
    }

    public void establecerElemento(int ren, int col, T dato) {
        datos[ren][col] = dato;
    }

    @SuppressWarnings("unchecked")
    public T obtenerElemento(int ren, int col) {
        return (T) datos[ren][col];
    }

    // Método simple para mostrar el tablero sin StringBuilder
    public void mostrarTablero() {
       //Línea superior
        System.out.print("  +");
        for(int i = 0; i < columnas; i++) {
            System.out.print("---+");
        }
        System.out.println();

        // Imprimir filas (de 8 a 1, para que negras queden arriba)
        for (int r = renglones - 1; r >= 0; r--) {
            System.out.print((r + 1) + " |"); // número de fila
            for (int c = 0; c < columnas; c++) {
                System.out.print(datos[r][c] + "|");
            }
            System.out.println(" " + (r + 1));
            
            // Línea separadora entre filas
            System.out.print("  +");
            for(int i = 0; i < columnas; i++) {
                System.out.print("---+");
            }
            System.out.println();
        }

        // Letras de columnas (a-h)
        System.out.print("   ");
        for (char c = 'a'; c < 'a' + columnas; c++) {
            System.out.print(" " + c + " ");
            System.out.print(" ");
        }
        System.out.println();
    }
}