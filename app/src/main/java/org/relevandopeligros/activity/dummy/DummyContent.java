package org.relevandopeligros.activity.dummy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.relevandopeligros.data.Peligro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Peligro> ITEMS = Lists.newArrayList();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Peligro> ITEM_MAP = Maps.newHashMap();

    static {
        // Add 3 sample items.
        for(int i = 0 ; i < 30 ; i++){
            Peligro peligro = new Peligro();
            peligro.setTitulo("Titulo "+ i);
            peligro.setDescripcion("Descripcion aasdasd test "+i );
            addItem(peligro);

        }
    }

    private static void addItem(Peligro item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getTitulo(), item);
    }

}
