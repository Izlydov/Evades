package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Array;

import objects.AllArenas;
import objects.Area;
public class AreaLoader {
    AllArenas allArenas;
    public static Array<Area> loadAllAreas() {
        FileHandle file = Gdx.files.internal("cc.json");
        Json json = new Json();

        AllArenas allArenas = json.fromJson(AllArenas.class, file);
        return allArenas.areas;
    }
    public static Area getAreaById(int areaId) {
        FileHandle file = Gdx.files.internal("cc.json");
        Json json = new Json();

        AllArenas allArenas = json.fromJson(AllArenas.class, file);
        return allArenas.areas.get(areaId - 1);
    }
}

