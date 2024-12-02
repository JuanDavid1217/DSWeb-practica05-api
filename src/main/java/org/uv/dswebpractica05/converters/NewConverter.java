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
public interface NewConverter <T, S> {
    public T dtotoEntity(S dto);
    public List<T> dtoListtoEntityList(List<S> dtoList);
}
