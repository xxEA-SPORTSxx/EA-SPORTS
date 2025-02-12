package CONTROLADOR;

import EXCEPTIONS.DatoNoValido;
import MODELO.Equipo;
import MODELO.EquipoDAO;
import MODELO.tipoEquipo;
import UTILITIS.CodigoAleatorioUnico;
import UTILITIS.SolicitarValidarDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class EquipoController {
    private static EquipoDAO equipoDAO;
    private Scanner sc;

    public EquipoController() {
        this.equipoDAO = new EquipoDAO();// Inicializa el DAO
        this.sc = new Scanner(System.in);
    }
    public void agregarEquipos(){
        System.out.println("\n--- Agregar Nuevo Equipo ---");

        String nombre = SolicitarValidarDatos.solicitarDato("Nombre", "Ingrese el nombre del equipo: ", "^[A-Za-zÁ-Úá-ú\\s]{2,50}$");
        LocalDate fechaFund = validarFecha("Fecha de fundacion","Teclea la fecha de fundacion del equipo");
        String tipoStr  = SolicitarValidarDatos.solicitarDato("Tipo de equipo","Teclea el tipo de equipo que seras (Atacante o Defensor)","[Atacante|Defensor]");
        tipoEquipo tipo = tipoEquipo.valueOf(tipoStr.toUpperCase());

        String idEquipo = CodigoAleatorioUnico.generarCodigoUnico();

        Equipo equipo = new Equipo(idEquipo,nombre,fechaFund,tipo ,new ArrayList<>(),new ArrayList<>());
        boolean agregado = equipoDAO.agregarEquipo(equipo);
        if (agregado){
            System.out.println("Equipo agregado correctamente");
        }else {
            System.out.println("Error: El equipo ya existe");
        }
    }
    public void eliminarEquipo(){
        System.out.println("\n--- Eliminar Equipo ---");
        String idEquipo = SolicitarValidarDatos.solicitarDato("ID del Equipo", "Ingrese el ID del equipo a eliminar: ", "");
        boolean eliminado = equipoDAO.eliminarEquipo(idEquipo);
        if (eliminado){
            System.out.println("Equipo eliminado correctamente");
        }else {
            System.out.println("Error: No se encontro el equipo con el ID proporcionado");
            }
    }







    public static LocalDate validarFecha(String dato, String mensaje){
        Scanner sc = new Scanner(System.in);
        boolean error = true;
        LocalDate fecha = null;
        String fecha_salida= "";
        while (error)
        {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.print(mensaje);

                fecha_salida = sc.nextLine();
                if (fecha_salida.isEmpty()){
                    throw new DatoNoValido(dato + " es un campo obligatorio");
                }
                fecha = LocalDate.parse(fecha_salida, formatter);
                error = false;
            } catch (DatoNoValido e) {
                System.out.println(e.getMessage());

            }catch (DateTimeParseException e){
                System.out.println(dato + " no tiene un formato adedcuado");
            }
        }
        return fecha;
    }
}