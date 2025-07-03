package it.ASDevSolution.DemoSimone.Utils;
import lombok.Data;

import java.util.List;

@Data
public class GenericListDTO <T> {

    private List<T> list;

}
