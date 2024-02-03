
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author PZ
 */
public class ProductosDao {
    
    Conexion cn= new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarProductos(Productos pro){
        String sql = "INSERT INTO productos(codigo,nombre,proveedor,stock,precio) VALUES (?,?,?,?,?)";
        try {
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getProveedor());
            ps.setInt(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.print(e.toString());
            return false;
        }
    }
    
    public void ConsultarProveedor(JComboBox proveedor){
        String sql = "SELECT nombre FROM proveedor";
        try {
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                proveedor.addItem(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
    }
    
    public List ListarProducto(){
    List <Productos> Listapro = new ArrayList();
    String sql= "SELECT * FROM productos";
    try{
        con=cn.getConnection();
        ps = con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
            Productos pro =  new Productos();
            pro.setId(rs.getInt("id"));
            pro.setCodigo(rs.getString("codigo"));
            pro.setNombre(rs.getString("nombre"));
            pro.setProveedor(rs.getString("proveedor"));
            pro.setStock(rs.getInt("stock"));
            pro.setPrecio(rs.getDouble("precio"));
            Listapro.add(pro);
        }
    }catch (SQLException e){
        System.out.println(e.toString());
    }
    return Listapro;
} 
    
    public boolean EliminarProductos(int id){
    String sql = "DELETE FROM productos WHERE id = ?";
    try{
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
        return true;
    }catch(SQLException e){
        System.out.println(e.toString());
        return false;
    }finally{
        try{
            con.close();
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }  
    }
}
    
    public boolean ModificarProductos(Productos pro) {
    String sql = "UPDATE productos SET codigo=?, nombre=?, proveedor=?, stock=?, precio=? WHERE id=?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, pro.getCodigo());
        ps.setString(2, pro.getNombre());
        ps.setString(3, pro.getProveedor());
        ps.setInt(4, pro.getStock());
        ps.setDouble(5, pro.getPrecio());
        ps.setInt(6, pro.getId());
        
        // Ejecutar la actualización
        ps.executeUpdate();
        
        // La ejecución fue exitosa
        return true;
    } catch (SQLException e) {
        System.out.println(e.toString());
        return false;
    } finally {
        // Cerrar la conexión en el bloque finally
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}
}
