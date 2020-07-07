package com.aoriseth.geomancy.registry;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerateModels {
    public static void main(String[] args) throws FileNotFoundException {
        String basePath = "src/main/resources/assets/geomancy/";
        String blockStatesPath = "blockstates/";
        String modelPath = "models/block/inscribed_divination_sand/";
        String texturePath = "textures/blocks/";

        List<List<Integer>> states = generatePossibleDivinationSandStates();
        generateDivinationSandBlockStates(basePath, blockStatesPath, states);
        generateDivinationSandModels(basePath, modelPath, states);
        generateDivinationSandTextures(basePath, texturePath, states);
    }

    private static void generateDivinationSandTextures(String basePath, String texturePath, List<List<Integer>> states) {
        states.forEach((state)->{
            try {
                BufferedImage texture = ImageIO.read(new File(basePath+texturePath + "divination_sand.png"));
                AtomicInteger verticalPosition = new AtomicInteger(1);
                state.forEach((inscription)->{
                    if (inscription == 1){
                        int horizontalPosition = 7;
                        drawLightDot(texture, horizontalPosition, verticalPosition.get());
                        drawDarkShade(texture, horizontalPosition, verticalPosition.get());
                    }
                    if (inscription == 2){
                        int horizontalPosition = 5;
                        drawLightDot(texture, horizontalPosition, verticalPosition.get() );
                        drawDarkShade(texture, horizontalPosition, verticalPosition.get());

                        horizontalPosition = 9;
                        drawLightDot(texture, horizontalPosition, verticalPosition.get() );
                        drawDarkShade(texture, horizontalPosition, verticalPosition.get());
                    }

                    verticalPosition.addAndGet(4);
                });

                ImageIO.write(texture, "PNG", new File(basePath+texturePath+String.format("inscribed_divination_sand/divination_sand_%s_%s_%s_%s.png", state.get(0), state.get(1), state.get(2), state.get(3))));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void drawDarkShade(BufferedImage texture, int horizontalPosition, int verticalPosition) {
        int lightRGB = -3032438;
        texture.setRGB(horizontalPosition+2, verticalPosition, lightRGB);
        texture.setRGB(horizontalPosition+2, verticalPosition+1, lightRGB);
        texture.setRGB(horizontalPosition, verticalPosition+2, lightRGB);
        texture.setRGB(horizontalPosition+1, verticalPosition+2, lightRGB);
    }

    private static void drawLightDot(BufferedImage texture, int horizontalPosition,  int verticalPosition) {
        int lightRGB = -1184821;
        texture.setRGB(horizontalPosition, verticalPosition, lightRGB);
        texture.setRGB(horizontalPosition+1, verticalPosition, lightRGB);
        texture.setRGB(horizontalPosition, verticalPosition+1, lightRGB);
        texture.setRGB(horizontalPosition+1, verticalPosition+1, lightRGB);
    }

    private static List<List<Integer>> generatePossibleDivinationSandStates() {
        List<List<Integer>> states = new ArrayList<>();
            for (int i1 = 0; i1 <= 2; i1++) {
                for (int i2 = 0; i2 <= 2; i2++) {
                    for (int i3 = 0; i3 <= 2; i3++) {
                        for (int i4 = 0; i4 <= 2; i4++) {
                            List<Integer> state = new ArrayList<>();
                            state.add(i1);
                            state.add(i2);
                            state.add(i3);
                            state.add(i4);
                            states.add(state);
                        }
                    }
                }
            }
        return states;
    }

    private static void generateDivinationSandModels(String basePath, String modelPath, List<List<Integer>> states) {
        states.forEach((state)->{
            try {
                PrintWriter printWriter = new PrintWriter(new File(basePath + modelPath + String.format("divination_sand_%s_%s_%s_%s.json", state.get(0), state.get(1), state.get(2), state.get(3))));
                printWriter.append(String.format(
                        "{\n" +
                        "    \"parent\": \"block/thin_block\",\n" +
                        "    \"textures\": {\n" +
                        "        \"particle\": \"minecraft:block/snow\",\n" +
                        "        \"texture\": \"geomancy:blocks/inscribed_divination_sand/divination_sand_%s_%s_%s_%s\"\n" +
                        "    },\n" +
                        "    \"elements\": [\n" +
                        "        {   \"from\": [ 0, 0, 0 ],\n" +
                        "            \"to\": [ 16, 2, 16 ],\n" +
                        "            \"faces\": {\n" +
                        "                \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#texture\", \"cullface\": \"down\" },\n" +
                        "                \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#texture\" },\n" +
                        "                \"north\": { \"uv\": [ 0, 14, 16, 16 ], \"texture\": \"#texture\", \"cullface\": \"north\" },\n" +
                        "                \"south\": { \"uv\": [ 0, 14, 16, 16 ], \"texture\": \"#texture\", \"cullface\": \"south\" },\n" +
                        "                \"west\":  { \"uv\": [ 0, 14, 16, 16 ], \"texture\": \"#texture\", \"cullface\": \"west\" },\n" +
                        "                \"east\":  { \"uv\": [ 0, 14, 16, 16 ], \"texture\": \"#texture\", \"cullface\": \"east\" }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}\n"
                , state.get(0), state.get(1), state.get(2), state.get(3)));
                printWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private static void generateDivinationSandBlockStates(String basePath, String blockStatesPath, List<List<Integer>> states) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(basePath + blockStatesPath + "divination_sand.json"));
        writer.append(
                "{\n" +
                "    \"variants\":{\n"
        );

        states.forEach((state)->{
            writer.append(
                    String.format(
                            "\"first=%s,second=%s,third=%s,fourth=%s\": {\n" +
                                    "            \"model\": \"geomancy:block/inscribed_divination_sand/divination_sand_%s_%s_%s_%s\"\n" +
                                    "        }",
                            state.get(0),state.get(1),state.get(2),state.get(3),state.get(0),state.get(1),state.get(2),state.get(3)
                    )
            );
            if (!(state.get(0) == 2 && state.get(1) == 2 && state.get(2) == 2 && state.get(3) == 2)){
                writer.append(",");
            }
        });
        writer.append(
                "    }\n" +
                "}"
        );


        writer.close();
    }
}
