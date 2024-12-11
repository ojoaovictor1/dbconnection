package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Carregar as propriedades do arquivo db.properties
                Properties props = loadProperties();

                // Obter a URL de conexão
                String url = props.getProperty("dburl");

                // Tentar obter a conexão com o banco
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                // Tratar exceção de conexão com o banco e lançar uma exceção personalizada
                throw new DbException("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return conn;
    }

    // Método para fechar a conexão com o banco de dados
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Tratar exceção ao fechar a conexão
                throw new DbException("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // Método para carregar as propriedades do arquivo db.properties
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            // Tratar erro ao carregar o arquivo de propriedades
            throw new DbException("Erro ao carregar o arquivo de propriedades: " + e.getMessage());
        }
    }
}
