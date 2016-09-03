package xyz.nickr.nbt;

import org.junit.Test;
import xyz.nickr.nbt.tags.CompoundTag;
import xyz.nickr.nbt.tags.ListTag;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FileTest {

    private CompoundTag parseFile(File file, NBTCompression compression) {
        try (FileInputStream fis = new FileInputStream(file)) {
            NBTCodec codec = new NBTCodec(ByteOrder.BIG_ENDIAN);
            BufferedInputStream bis = new BufferedInputStream(fis);
            if (compression == null)
                compression = NBTCompression.detect(bis);
            return codec.decode(bis, compression).getAsCompoundTag();
        } catch (IOException e) {
            fail(e.getClass().getSimpleName() + ": " + e.getMessage());
            return null;
        }
    }

    @Test
    public void testHelloWorld() {
        File file = new File("nbt/hello_world.nbt");
        CompoundTag tag = parseFile(file, NBTCompression.UNCOMPRESSED);

        assertTrue(tag.getName().isPresent());
        assertEquals("hello world", tag.getName().get());
        assertEquals(1, tag.size());
        assertTrue(tag.has("name"));
        assertTrue(tag.isString("name"));
        assertEquals("Bananrama", tag.getAsString("name"));
    }

    @Test
    public void testBigtest() {
        File file = new File("nbt/bigtest.nbt");
        CompoundTag tag = parseFile(file, NBTCompression.GZIP);

        assertTrue(tag.getName().isPresent());
        assertEquals("Level", tag.getName().get());
        assertEquals(11, tag.size());

        String byteArrayTest = "byteArrayTest (the first 1000 values of (n*n*255+n*7)%100, starting with n=0 (0, 62, 34, 16, 8, ...))";
        assertTrue(tag.has(byteArrayTest));
        byte[] bytes = tag.getAsByteArray(byteArrayTest);
        assertEquals(1000, bytes.length);
        for (int i = 0; i < 1000; i++) {
            assertEquals((byte) ((i * i * 255 + i * 7) % 100), bytes[i]);
        }

        assertTrue(tag.has("byteTest"));
        assertEquals(127, tag.getAsNumber("byteTest").byteValue());

        assertTrue(tag.has("doubleTest"));
        assertEquals(0.4931287132182315, tag.getAsNumber("doubleTest").doubleValue(), 0.00000000001);

        assertTrue(tag.has("floatTest"));
        assertEquals(0.4982314705848694, tag.getAsNumber("floatTest").floatValue(), 0.00000000001f);

        assertTrue(tag.has("intTest"));
        assertEquals(2147483647, tag.getAsNumber("intTest").intValue());

        assertTrue(tag.has("listTest (compound)"));
        ListTag listTest = tag.getAsListTag("listTest (compound)");
        assertEquals(2, listTest.size());

        for (int i = 0; i < 2; i++) {
            assertTrue(listTest.get(i).isCompoundTag());
            CompoundTag ct = listTest.get(i).getAsCompoundTag();
            assertEquals(2, ct.size());
            assertTrue(ct.has("created-on"));
            assertTrue(ct.has("name"));
            assertEquals(1264099775885L, ct.getAsNumber("created-on").longValue());
            assertEquals("Compound tag #" + i, ct.getAsString("name"));
        }

        assertTrue(tag.has("listTest (long)"));
        listTest = tag.getAsListTag("listTest (long)");
        for (int i = 0; i < 5; i++) {
            assertEquals(11L + i, listTest.getAsNumber(i));
        }

        assertEquals(9223372036854775807L, tag.getAsNumber("longTest").longValue());

        assertTrue(tag.has("nested compound test"));
        CompoundTag nestedTag = tag.getAsCompoundTag("nested compound test");
        assertEquals(2, nestedTag.size());

        assertTrue(nestedTag.has("egg"));
        assertTrue(nestedTag.isCompoundTag("egg"));
        assertTrue(nestedTag.getAsCompoundTag("egg").has("name"));
        assertEquals("Eggbert", nestedTag.getAsCompoundTag("egg").getAsString("name"));
        assertTrue(nestedTag.getAsCompoundTag("egg").has("value"));
        assertEquals(0.5f, nestedTag.getAsCompoundTag("egg").getAsNumber("value").floatValue(), 0f);

        assertTrue(nestedTag.has("ham"));
        assertTrue(nestedTag.isCompoundTag("ham"));
        assertTrue(nestedTag.getAsCompoundTag("ham").has("name"));
        assertEquals("Hampus", nestedTag.getAsCompoundTag("ham").getAsString("name"));
        assertTrue(nestedTag.getAsCompoundTag("ham").has("value"));
        assertEquals(0.75f, nestedTag.getAsCompoundTag("ham").getAsNumber("value").floatValue(), 0f);

        assertTrue(tag.has("shortTest"));
        assertEquals(32767, tag.getAsNumber("shortTest").shortValue());

        assertTrue(tag.has("stringTest"));
        assertEquals("HELLO WORLD THIS IS A TEST STRING ÅÄÖ!", tag.getAsString("stringTest"));
    }

    @Test
    public void testLevel() {
        File file = new File("nbt/level.dat");
        CompoundTag tag = parseFile(file, NBTCompression.GZIP);

        assertEquals(1, tag.size());
        assertTrue(tag.has("Data"));
        assertTrue(tag.isCompoundTag("Data"));

        tag = tag.getAsCompoundTag("Data");

        assertTrue(tag.has("allowCommands"));
        assertEquals(0, tag.getAsNumber("allowCommands").byteValue());

        assertTrue(tag.has("BorderCenterX"));
        assertEquals(0.0, tag.getAsNumber("BorderCenterX").doubleValue(), 0.0);

        assertTrue(tag.has("BorderCenterZ"));
        assertEquals(0.0, tag.getAsNumber("BorderCenterZ").doubleValue(), 0.0);

        assertTrue(tag.has("BorderDamagePerBlock"));
        assertEquals(0.2, tag.getAsNumber("BorderDamagePerBlock").doubleValue(), 0.00000000001);

        assertTrue(tag.has("BorderSafeZone"));
        assertEquals(5.0, tag.getAsNumber("BorderSafeZone").doubleValue(), 0.0);

        assertTrue(tag.has("BorderSize"));
        assertEquals(60000000.0, tag.getAsNumber("BorderSize").doubleValue(), 0.0);

        assertTrue(tag.has("BorderSizeLerpTarget"));
        assertEquals(60000000.0, tag.getAsNumber("BorderSizeLerpTarget").doubleValue(), 0.0);

        assertTrue(tag.has("BorderSizeLerpTime"));
        assertEquals(0, tag.getAsNumber("BorderSizeLerpTime").longValue());

        assertTrue(tag.has("BorderWarningBlocks"));
        assertEquals(5.0, tag.getAsNumber("BorderWarningBlocks").doubleValue(), 0.0);

        assertTrue(tag.has("BorderWarningTime"));
        assertEquals(15.0, tag.getAsNumber("BorderWarningTime").doubleValue(), 0.0);

        assertTrue(tag.has("clearWeatherTime"));
        assertEquals(0, tag.getAsNumber("clearWeatherTime").intValue());

        assertTrue(tag.has("DayTime"));
        assertEquals(5062614, tag.getAsNumber("DayTime").longValue());

        assertTrue(tag.has("Difficulty"));
        assertEquals(1, tag.getAsNumber("Difficulty").byteValue());

        assertTrue(tag.has("DifficultyLocked"));
        assertEquals(0, tag.getAsNumber("DifficultyLocked").byteValue());

        assertTrue(tag.has("GameType"));
        assertEquals(0, tag.getAsNumber("GameType").intValue());

        assertTrue(tag.has("generatorName"));
        assertEquals("default", tag.getAsString("generatorName"));

        assertTrue(tag.has("generatorOptions"));
        assertEquals("", tag.getAsString("generatorOptions"));

        assertTrue(tag.has("generatorVersion"));
        assertEquals(1, tag.getAsNumber("generatorVersion").intValue());

        assertTrue(tag.has("hardcore"));
        assertEquals(0, tag.getAsNumber("hardcore").byteValue());

        assertTrue(tag.has("initialized"));
        assertEquals(1, tag.getAsNumber("initialized").byteValue());

        assertTrue(tag.has("LastPlayed"));
        assertEquals(1419495308129L, tag.getAsNumber("LastPlayed").longValue());

        assertTrue(tag.has("LevelName"));
        assertEquals("HuabanWorld", tag.getAsString("LevelName"));

        assertTrue(tag.has("MapFeatures"));
        assertEquals(1, tag.getAsNumber("MapFeatures").byteValue());

        assertTrue(tag.has("raining"));
        assertEquals(0, tag.getAsNumber("raining").byteValue());

        assertTrue(tag.has("rainTime"));
        assertEquals(22220, tag.getAsNumber("rainTime").intValue());

        assertTrue(tag.has("RandomSeed"));
        assertEquals(37911926, tag.getAsNumber("RandomSeed").longValue());

        assertTrue(tag.has("SizeOnDisk"));
        assertEquals(0, tag.getAsNumber("SizeOnDisk").longValue());

        assertTrue(tag.has("SpawnX"));
        assertEquals(-229, tag.getAsNumber("SpawnX").intValue());

        assertTrue(tag.has("SpawnY"));
        assertEquals(68, tag.getAsNumber("SpawnY").intValue());

        assertTrue(tag.has("SpawnZ"));
        assertEquals(365, tag.getAsNumber("SpawnZ").intValue());

        assertTrue(tag.has("thundering"));
        assertEquals(0, tag.getAsNumber("thundering").byteValue());

        assertTrue(tag.has("thunderTime"));
        assertEquals(98286, tag.getAsNumber("thunderTime").intValue());

        assertTrue(tag.has("Time"));
        assertEquals(32075154, tag.getAsNumber("Time").longValue());

        assertTrue(tag.has("version"));
        assertEquals(19133, tag.getAsNumber("version").longValue());

        assertTrue(tag.has("GameRules"));
        assertTrue(tag.isCompoundTag("GameRules"));
        tag = tag.getAsCompoundTag("GameRules");

        Map<String, String> gameRules = new HashMap<String, String>() {
            {
                put("commandBlockOutput", "true");
                put("doDaylightCycle", "true");
                put("doEntityDrops", "true");
                put("doFireTick", "false");
                put("doMobLoot", "true");
                put("doMobSpawning", "true");
                put("doTileDrops", "true");
                put("keepInventory", "false");
                put("logAdminCommands", "true");
                put("mobGriefing", "false");
                put("naturalRegeneration", "true");
                put("randomTickSpeed", "3");
                put("reducedDebugInfo", "false");
                put("sendCommandFeedback", "true");
                put("showDeathMessages", "true");
            }
        };

        for (Map.Entry<String, String> gameRule : gameRules.entrySet()) {
            assertTrue(tag.has(gameRule.getKey()));
            assertEquals(gameRule.getValue(), tag.getAsString(gameRule.getKey()));
        }
    }

}
