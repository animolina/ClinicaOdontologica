package com.example.dh.ClinicaOdontologica.Repository;
import java.util.List;


public interface IDaoRepository<T> {
    public T agregar(T t) throws Exception;
    public T buscarPorId(Long id) throws Exception;
    public List<T> buscarTodos() throws Exception;
    public T actualizar(T t) throws Exception;
    public void eliminarPorId(Long id) throws Exception;

    public void eliminarTodos() throws Exception;

}
