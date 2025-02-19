package org.example;

import com.ejemplo.config.HibernateUtil;
import com.ejemplo.modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        // Crear y guardar un nuevo usuario
        Usuario usuario = new Usuario("Juan Pérez", "juan@example.com", "1233");
        session.persist(usuario);


        transaction.commit();
        session.close();

        System.out.println("¡Usuario guardado con éxito!");
    }
}