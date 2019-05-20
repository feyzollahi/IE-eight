package model.Repo;


import dataLayer.dataMappers.ProjectMapper.AdvancedProjectMapper;
import dataLayer.dataMappers.UserMapper.AdvancedUserMapper;
import dataLayer.dataMappers.UserMapper.UserMapper;
import model.Exceptions.DupEndorse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRepo {
    public static boolean isSetRepo = false;
    public static void print(Object o){
        System.out.println(o);
    }
    public static Object getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        JSONParser parser = new JSONParser();
        Object object = parser.parse(result.toString());
        return object;
    }
    public static void setRepo() throws Exception, DupEndorse {
//        UserMapper userMapper = new UserMapper();
//        AdvancedUserMapper advancedUserMapper = new AdvancedUserMapper();
//        AdvancedProjectMapper advancedProjectMapper = new AdvancedProjectMapper();
//        return;
        GetRepo.isSetRepo = true;
        ProjectsRepo projectsRepo = ProjectsRepo.getInstance();
        projectsRepo.setRepo();
        SkillsRepo skillsRepo = SkillsRepo.getInstance();
        skillsRepo.setRepo();
        UsersRepo usersRepo = UsersRepo.getInstance();
        usersRepo.setRepoInDataBase();
    }
    public static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
        JSONObject mergedJSON = new JSONObject();
        try {
            mergedJSON = new JSONObject(json1, JSONObject.getNames(json1));
            for (String crunchifyKey : JSONObject.getNames(json2)) {
                mergedJSON.put(crunchifyKey, json2.get(crunchifyKey));
            }

        } catch (JSONException e) {
            throw new RuntimeException("JSON Exception" + e);
        }
        return mergedJSON;
    }
}
