package com.h3dg3Wytch;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by h3dg3wytch on 2/23/17.
 */


public class CustomJSONParser {

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();

        try {
            JSONObject json;
            if(args.length == 0)
               throw new FileNotFoundException();
            else
                json = (JSONObject) parser.parse(new FileReader(args[0]));
            Set<String> keys = json.keySet();
            //We do this section so we can code defensively. In case we get a malformed schema, or a test case
            //that doesn't conform
            String firstName = null, lastName = null;
            JSONArray shoppingCart = null;
            for (String key : keys) {
                switch (key) {
                    case "firstName":
                        firstName = (String) json.get(key);
                        break;
                    case "lastName":
                        lastName = (String) json.get(key);
                        break;
                    case "shoppingCart":
                        shoppingCart = (JSONArray) json.get("shoppingCart");
                        break;
                }
            }

            if(firstName != null && lastName != null && shoppingCart != null){

                JSONObject result =  formatJSONtoOutput(firstName, lastName, shoppingCart);
                System.out.println(result);

            }else{
                throw new JSONMalformedException();
            }

        } catch (JSONMalformedException e) {
        } catch (ParseException e) {
            System.out.println("Cannot parse file. Please enter valid input");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file. Please enter valid input");
        } catch (IOException e) {
            System.out.println("Something went wrong, please check your inputs");
        }


    }

    private static JSONObject formatJSONtoOutput(String firstName, String lastName, JSONArray shoppingCart) {
        JSONObject result = new JSONObject();

        JSONObject name = new JSONObject();
        name.put("first", firstName);
        name.put("last", lastName);

        result.put("name", name);

        HashMap<String, Integer> shoppingCartHashMap = new HashMap<>();
        List<String> itemsEnteredUnqiueSet = new ArrayList<>();
        for(int i = 0; i < shoppingCart.size(); i++){
            String item = ((String) shoppingCart.get(i)).toLowerCase();
            if(shoppingCartHashMap.containsKey(item)){
                shoppingCartHashMap.put(item, shoppingCartHashMap.get(item) + 1);
            }else{
                boolean itemNotInSet = true;
                for(String itemInCart: itemsEnteredUnqiueSet){
                    if(item.contains(itemInCart) || item.equals(itemInCart)){
                        shoppingCartHashMap.put(itemInCart, shoppingCartHashMap.get(itemInCart) + 1);
                        itemNotInSet = false;
                        break;
                    }
                }
                if(itemNotInSet){
                    itemsEnteredUnqiueSet.add(item);
                    shoppingCartHashMap.put(item, 1);
                }

            }
        }

        JSONObject shoppingCartObject = new JSONObject();
        for(String key : shoppingCartHashMap.keySet()){

            shoppingCartObject.put(key, shoppingCartHashMap.get(key));
        }
        result.put("shoppingCart", shoppingCartObject);
        return result;
    }
}
