package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBUtils.MysqlConexion;
import Entidad.Cliente;
import Entidad.TipoCliente;

public class ClienteModel {
  
	public List<Cliente> ListarCliente() {
		ArrayList<Cliente> ac = new ArrayList<Cliente>();
		Cliente cli = null;
		TipoCliente tip = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MysqlConexion.getConexion();
			String sql = "select c.idCliente,c.nombres,c.apellidos,c.fechaNacimiento,t.nombre from cliente c"
					    +" inner join tipo_cliente as t on c.idTipoCliente = t.idTpoCliente";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				cli = new Cliente();
				cli.setIdCliente(rs.getInt(1));
				cli.setNombres(rs.getString(2));
				cli.setApellidos(rs.getString(3));
				cli.setFechaNacimiento(rs.getDate(4));
				tip = new TipoCliente();
				tip.setCategoriaCliente(rs.getString(5));
				cli.setTipo(tip);
				ac.add(cli);
			}
		}catch(SQLException e) {
			System.err.println(">> Error al listar los cliente : "+e);
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println(">> Error al cerrar la conexión");
				}
			}
		}
		return ac;
	}
	public List<Cliente> Buscar(String tipoFiltro,String valor) {
		ArrayList<Cliente> ac = new ArrayList<Cliente>();
		Cliente cli = null;
		TipoCliente tip = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MysqlConexion.getConexion();
			String sql = "select c.idCliente,c.nombres,c.apellidos,c.fechaNacimiento,t.nombre from cliente c"
					    +" inner join tipo_cliente as t on c.idTipoCliente = t.idTpoCliente"
					    +" where "+tipoFiltro+" like '"+valor+"%'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				cli = new Cliente();
				cli.setIdCliente(rs.getInt(1));
				cli.setNombres(rs.getString(2));
				cli.setApellidos(rs.getString(3));
				cli.setFechaNacimiento(rs.getDate(4));
				tip = new TipoCliente();
				tip.setCategoriaCliente(rs.getString(5));
				cli.setTipo(tip);
				ac.add(cli);
			}
		}catch(SQLException e) {
			System.err.println(">> Error en la consulta de los clientes : "+e);
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println(">> Error al cerrar la conexión"+e);
				}
			}
		}
		return ac;
	}
}
