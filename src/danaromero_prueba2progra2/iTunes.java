/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danaromero_prueba2progra2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Dana Romero
 */
public class iTunes {
    
    RandomAccessFile rcodigos;
    RandomAccessFile rsongs ;
    RandomAccessFile rdownloads;
    
    //Constructor
    
    public iTunes () throws IOException{
        rcodigos = new RandomAccessFile("codigos.itn","rw");
        rsongs = new RandomAccessFile("songs.itn","rw");
        rdownloads= new RandomAccessFile("downloads.itn","rw");
        if ( rcodigos.length()==0){
            rcodigos.writeInt(1);
            rcodigos.writeInt(1);
            
        }
    }
    
    
    public int getCodigo ( long offset) throws IOException{
        if(offset == 1){
            rcodigos.seek(0);
            int codDisponible = rcodigos.readInt();
            rcodigos.seek(0);
            rcodigos.writeInt(1+codDisponible);
            return codDisponible;
        }else{
            rcodigos.seek(0);
            rcodigos.readInt();
            int codDisponible = rcodigos.readInt();
            rcodigos.seek(0);
            rcodigos.readInt();
            rcodigos.writeInt(1+codDisponible);
            return codDisponible;
        }
    }
    
    //Función para buscar la canción según código
    
    public boolean searchSong(int code) throws IOException{
        rsongs.seek(0);
        while(rsongs.getFilePointer()<rsongs.length()){
            if(rsongs.readInt()==code){
            return true;
            }
            rsongs.readUTF();
            rsongs.readUTF();
            rsongs.skipBytes(16);

        }
        rsongs.seek(0);
        return false;
    }
    
    //Función para agregar canción
    
    public void addSong(String nombre, String cantante, double precio) throws IOException{
        rsongs.seek(rsongs.length());
        rsongs.writeInt(getCodigo(1));
        rsongs.writeUTF(nombre);
        rsongs.writeUTF(cantante);
        rsongs.writeDouble(precio);
        //Estrellas
        rsongs.writeInt(0);
        //Veces que se le ha dado review
        rsongs.writeInt(0);
        
    }
    
    public void reviewSong(int code, int stars) throws IOException{
        try{
            if(code>=0 && code<=5){
            if(searchSong(code)){
                rsongs.readUTF();
                rsongs.readUTF();
                rsongs.readDouble();
                int cantActualStars = rsongs.readInt();
                int cantActualReview = rsongs.readInt();
                //Set puntero después del código
                searchSong(code);
                rsongs.readUTF();
                rsongs.readUTF();
                rsongs.readDouble();
                rsongs.writeInt(stars+cantActualStars);
                rsongs.writeInt(1+cantActualReview);
            }

        }else{
            throw new IllegalArgumentException();
        }
        }catch(IllegalArgumentException ex){
            System.out.println("Las stars deben estar entre 0 y 5");
        }
    }
        
    
    
    public void downloadSong(int codeSong, String cliente) throws IOException{
        if(searchSong(codeSong)){
            String titulo = rsongs.readUTF();
            rsongs.readUTF();
            double precio = rsongs.readDouble();
            rdownloads.writeInt(getCodigo(4));
            Long hoy = Calendar.getInstance().getTimeInMillis();
            rdownloads.writeLong(hoy);
            rdownloads.writeInt(codeSong);
            rdownloads.writeUTF(cliente);
            rdownloads.writeDouble(precio);
            System.out.println("GRACIAS "+ cliente+" por bajar "+ titulo+" a un costo de Lps. "+precio);
        }else{
            System.out.println("No se encontró la canción con código "+ codeSong);
        }
    }
    
    public void songs(String txtFile) throws IOException{
        File reporte = new File(txtFile);
        FileWriter fw = new FileWriter (reporte, false);
        rsongs.seek(0);
        while(rsongs.getFilePointer()<rsongs.length()){
            int codigo = rsongs.readInt();
            String titulo = rsongs.readUTF();
            String cantante = rsongs.readUTF();
            double precio = rsongs.readDouble();
            double rating = rsongs.readInt()/rsongs.readInt();
            String info = "Código: "+codigo+" - Título: "+titulo+" - Cantante: "+ cantante+" - "+
                    "Precio: "+ precio+" - Rating: "+ rating+"\n";
            System.out.println(info);
            fw.write(info);
            fw.flush();
            
        }
    }
    
    public void infoSong(int codeSong) throws IOException{
        String infoD="";
        String infoS ="";                
        if(searchSong(codeSong)){
            //Datos de la Canción
            String tituloS = rsongs.readUTF();
            String cantanteS = rsongs.readUTF();
            double precioS = rsongs.readDouble();
            int starsS = rsongs.readInt();
            int reviewsS = rsongs.readInt();
            double ratingS = starsS/reviewsS;
            infoS = "Código: "+codeSong+" - Título: "+tituloS+" - Cantante: "+ cantanteS
                    +" - "+"Precio: "+ precioS+" - Stars: "+starsS+" - Reviews: "+reviewsS+" - Rating: "+ ratingS+"\n";
            //Datos de Downloads
            
            int contDownloads=0;
            rdownloads.seek(0);
                while(rdownloads.getFilePointer()<rdownloads.length()){
                int codigoDownload = rdownloads.readInt();
                Long fecha = rdownloads.readLong();
               Date fechaD = new Date(fecha);
                int codSongD = rdownloads.readInt();
                String clienteD = rdownloads.readUTF();
                double precioD = rdownloads.readDouble();

                if(codeSong == codSongD){

                    infoD = infoD +"Código del Download: "+codigoDownload+" - Fecha: "+fechaD+" - Código Canción: "+
                             codSongD+" - Cliente: "+clienteD+" - Precio: "+precioD+"\n";
                    contDownloads++;
                }
            }
            
           //Imprimir Datos
            System.out.println("***Song***\n"+infoS+"***Downloads***\n"+infoD+"\nCantidad de Descargas: "+contDownloads);
        }
    }
    
}
