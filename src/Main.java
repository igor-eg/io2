import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    //public static StringBuilder sb = new StringBuilder("Логи:\n");

    public static void main(String[] args) {

        saveGame("H://JAVA/Games/savegames/save1.dat", 94, 10, 2, 254.32);
        saveGame("H://JAVA/Games/savegames/save2.dat", 100, 11, 3, 354.35);
        saveGame("H://JAVA/Games/savegames/save3.dat", 110, 12, 4, 444.65);
        archiving("H://JAVA/Games/savegames", "H://JAVA/Games/savegames/zip_save.zip", "packed_notes",
                ".dat");

        deletingFile("H://JAVA/Games/savegames", ".dat");
    }

    public static void saveGame(String directoryName, int health, int weapons, int lvl, double distance) {
        GameProgress gameProgress = new GameProgress(health, weapons, lvl, distance);
        // откроем выходной поток для записи в файл
        try (FileOutputStream fos = new FileOutputStream(directoryName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // запишем экземпляр класса в файл
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void archiving(String directoryName, String zip, String fileName, String expansion) {
        int i = 1;


        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip))) {

            File dir = new File(directoryName);
            for (File item : dir.listFiles()) {
                // проверим, является ли объект каталогом
                if (!item.isDirectory() && item.getName().contains(expansion)) {

                    String name = fileName + i + ".dat";

                    try (FileInputStream fis = new FileInputStream("H://JAVA/Games/savegames/" + item.getName())) {

                        ZipEntry entry = new ZipEntry(name);
                        zout.putNextEntry(entry);


                        // считываем содержимое файла в массив
                        byte[] buffer = new byte[fis.available()];
                        fis.read(buffer);
                        // добавляем содержимое к архиву
                        zout.write(buffer);
                        // закрываем текущую запись для новой записи
                        zout.closeEntry();

                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());

                    }
                }
                i++;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deletingFile(String directoryName, String expansion) {
        File dir = new File(directoryName);
        for (File item : dir.listFiles()) {
            // проверим, является ли объект каталогом
            if (!item.isDirectory() && item.getName().contains(expansion)) {
                item.delete();
            }
        }
    }
}

