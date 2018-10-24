package com.sbda.gamal.sbda_android_porject;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parser extends AsyncTask<Void,Integer,Integer> {
    private User u;
    private Context c;
    private UserProfile user;
    private String data;


    ProgressDialog pd;

    public Parser(Context c, String data, UserProfile user) {
        this.c = c;
        this.data = data;
        this.user = user;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected Integer doInBackground(Void... params) {

        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if(integer == 1)
        {
            user.username.setText(u.getName());
            user.usernameF.setText(u.getName());
            user.email.setText(u.getEmail());
            user.mobile.setText(u.getMobile());
            user.weight.setText(u.getWeight()+"");
            user.weightF.setText((u.getWeight()+""));
            user.tall.setText(u.getHeight()+"");
            user.tallF.setText((u.getHeight()+""));
            user.city.setText(u.getAddress());
            user.diseases.setText((u.getDiseases()));
            user.bloodgroup.setText((u.getBloodgroup()));
//            user.age.setText(u.getBirthdate());
            user.diseases.setText(u.getDiseases());
            user.gender.setChecked(u.getSex().equals("female")?true:false);
            



        }else
        {
            Toast.makeText(c,"Unable to Parse",Toast.LENGTH_SHORT).show();
        }


    }

    //PARSE RECEIVED DATA
    private int parse()
    {

        JSONObject jsonResponse;
        try
        {
            jsonResponse = new JSONObject(data);
            JSONArray jos = jsonResponse.getJSONArray("result");

            for(int i=0;i<jos.length();i++){
                JSONObject jo = jos.getJSONObject(i);
                if(jo.getString("email").equals(user.emailPref)) {
                    u = new User();
                    u.setId(Integer.parseInt(jo.getString("id")));
                    u.setName(jo.getString("name"));
                    u.setPassword(jo.getString("password"));
                    u.setEmail(jo.getString("email"));
                    u.setAddress(jo.getString("address"));
                    u.setBirthdate(jo.getString("bd"));
                    u.setDiseases(jo.getString("diseases"));
                    u.setBloodgroup(jo.getString("lastdonationdate"));
                    u.setMembership(Integer.parseInt(jo.getString("membership")));
                    u.setSex(jo.getString("sex"));
                    u.setMobile(jo.getString("mobile"));
                    u.setBloodgroup(jo.getString("bloodgroup"));
                    u.setWeight(Float.parseFloat(jo.getString("weight")));
                    u.setWeightF(Float.parseFloat(jo.getString("weight")));
                    u.setHeight(Float.parseFloat(jo.getString("height")));
                }
            }




            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

