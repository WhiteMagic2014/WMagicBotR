package com.whitemagic2014.gmpfunction;

import com.github.WhiteMagic2014.function.GmpFunction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Function 池
 * @author: magic chen
 * @date: 2023/12/1 15:56
 **/
@Component
public class FunctionPool {

    private List<GmpFunction> pool = new ArrayList<>();

    public void addFunction(GmpFunction function) {
        pool.add(function);
    }

    public List<GmpFunction> functions() {
        return pool;
    }

}
