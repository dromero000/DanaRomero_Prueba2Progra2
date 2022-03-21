/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danaromero_prueba2progra2;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Dana Romero
 */
public class Main {
    public static void main(String[] args) throws IOException {
        iTunes itunes = new iTunes();
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        System.out.println("***ITUNES***");
        int opcion=0;
        
        do{
            System.out.println("Menú\n1 - Add Song"+"\n2 - Review Song"+"\n3 - Download Song"
                    +"\n4 - Songs Reporte"+"\n5 - Song Information"+"\n6 - Salir");
            System.out.println("Escoja una opción:");
            opcion = leer.nextInt();
            switch(opcion){
                case 1:
                    System.out.println("Add Song");
                    System.out.print("Ingrese nombre de la canción: ");
                    String nombre = leer.next();
                    System.out.print("Ingrese nombre del cantante: ");
                    String cantante = leer.next();
                    System.out.print("Ingrese precio: ");
                    Double precio = leer.nextDouble();
                    itunes.addSong(nombre, cantante,precio);
                    break;
                case 2:
                    System.out.println("Review Song");
                    System.out.print("Ingrese código de la canción: ");
                    int cod = leer.nextInt();
                    System.out.print("Ingrese estrellas: ");
                    int star = leer.nextInt();
                    itunes.reviewSong(cod, star);
                    break;
                case 3 :
                    System.out.println("Download Song");
                    System.out.print("Ingrese código de la canción: ");
                    int code = leer.nextInt();
                    System.out.print("Ingrese nombre del cliente: ");
                    String cliente = leer.next();
                    itunes.downloadSong(code, cliente);
                    break;
                case 4:
                    System.out.println("Songs Reporte");
                    System.out.print("Ingrese dirección del archivo: ");
                    String dir = leer.next();
                    itunes.songs(dir);
                    break;
                case 5:
                    System.out.println("Song Information");
                    System.out.print("Ingrese código de la canción: ");
                    int codigo = leer.nextInt();
                    itunes.infoSong(codigo);
                    break;
                case 6:
                    System.out.println("Gracias");
                    break;
            }
        }while(opcion!=6);
    }
    
    
    
}
