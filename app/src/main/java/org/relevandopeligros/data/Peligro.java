package org.relevandopeligros.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by mauricio.heredia 8/28/14.
 */
public class Peligro implements Serializable{
    private String id;
    private String titulo;
    private String descripcion;
    private List<ImagenPeligro> imagenes = Collections.emptyList();
    private String ubicacion;
    private String siteUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ImagenPeligro> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenPeligro> imagenes) {
        this.imagenes = imagenes;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    public String getDescripcion() {

        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTitulo(String titulo) {

        this.titulo = titulo;
    }

    public String getTitulo() {

        return titulo;
    }

    public static class ImagenPeligro implements Serializable{
        private String titulo;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        private String path;
    }

    public static class ImagenPeligroUpload implements Serializable{
        private String titulo;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        private String path;
    }
}
