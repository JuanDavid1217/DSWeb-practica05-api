/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;

/**
 *
 * @author David
 */
public interface RegisteredConverter <T, S> {
    public S entitytoDTO(T entity);
    public List<S> entityListtoDTOList(List<T> entityList);
}
