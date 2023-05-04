package edu.pucmm.eict.darvybm.services;

import edu.pucmm.eict.darvybm.modelos.CarritoItem;
import edu.pucmm.eict.darvybm.modelos.Ventas;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;

public class CockroachServices {

    private static CockroachServices instanciaUnica;

    private CockroachServices() throws SQLException {
        try (Connection conexion = getConection(); Statement sentencia = conexion.createStatement()) {

            // Validar si las tablas ya existen
            ResultSet rs = conexion.getMetaData().getTables(null, null, "Ventas", null);
            boolean existeCarritoItem = rs.next();
            rs = conexion.getMetaData().getTables(null, null, "CarritoItem", null);
            boolean existeVentas = rs.next();

            if (existeCarritoItem || existeVentas) {
                System.out.println("Las tablas ya existen en la base de datos");
            } else {
                // Crear las tablas
                String sqlCarritoItem = "CREATE TABLE CarritoItem (\n" +
                        "    id UUID PRIMARY KEY DEFAULT uuid_v4(),\n" +
                        "    cantidad INT,\n" +
                        "    producto_id INT,\n" +
                        "    venta_id INT,\n" +
                        "    FOREIGN KEY (venta_id) REFERENCES Ventas(id)\n" +
                        ");\n";

                String sqlVenta = "CREATE TABLE Ventas (\n" +
                        "    id UUID PRIMARY KEY,\n" +
                        "    fecha_compra DATE,\n" +
                        "    nombre_cliente VARCHAR(255)\n" +
                        ");";

                sentencia.executeUpdate(sqlVenta);
                sentencia.executeUpdate(sqlCarritoItem);
                addSequenceCarritoItem();

                System.out.println("Tablas creadas exitosamente");
            }

        } catch (SQLException ex) {
            System.out.println("Error al crear las tablas: " + ex.getMessage());
        }

    }

    public static CockroachServices getInstance() throws SQLException {
        if (instanciaUnica == null) {
            instanciaUnica = new CockroachServices();
        }
        return instanciaUnica;
    }

    public Connection getConection() throws SQLException, SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(System.getenv("JDBC_DATABASE_URL"));
        ds.setSslMode( "require" );
        return ds.getConnection();
    }

    public void addCarritoItem(CarritoItem carritoItem) {
        try (Connection conexion = getConection()) {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO CarritoItem (id, cantidad, producto_id, venta_id) VALUES (?, ?, ?, ?)");

            ps.setObject(1, carritoItem.getId());
            ps.setInt(2, carritoItem.getCantidad());
            ps.setInt(3, carritoItem.getProducto().getId());
            ps.setObject(4, carritoItem.getVenta().getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al insertar el CarritoItem: " + ex.getMessage());
        }
    }



    public int getLastIDVentas() {
        int lastID = -1;
        try (Connection conn = getConection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS last_id_ventas FROM ventas");
            if (rs.next()) {
                lastID = rs.getInt("last_id_ventas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastID;
    }

    public int getLastIDCarritoItem() {
        int lastID = -1;
        try (Connection conn = getConection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS last_id_carrito FROM carritoitem");
            if (rs.next()) {
                lastID = rs.getInt("last_id_carrito");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastID;
    }
    public void updateCarritoItem() {
        int lastID = -1;
        try (Connection conn = getConection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS last_id_carrito FROM carritoitem");
            if (rs.next()) {
                lastID = rs.getInt("last_id_carrito");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSequenceCarritoItem(){

        try (Connection conn = getConection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM information_schema.sequences WHERE sequence_name = 'carritoitem_seq'");
            rs.next();
            int count = rs.getInt(1);
            rs.close();

            if (count == 0) {
                // Create a sequence named carritoitem_seq
                String sql = "CREATE SEQUENCE carritoitem_seq START 1 INCREMENT 1 MAXVALUE 9999999999999999999999999 CACHE 20";
                stmt.executeUpdate(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void addVenta(Ventas venta) {
        try {
            PreparedStatement ps = getConection().prepareStatement("INSERT INTO Ventas (id, fecha_compra, nombre_cliente) VALUES (?, ?, ?)");
            ps.setObject(1, venta.getId());
            ps.setDate(2, new java.sql.Date(venta.getFechaCompra().getTime()));
            ps.setString(3, venta.getNombreCliente());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al insertar la Venta: " + ex.getMessage());
        }
    }
}
