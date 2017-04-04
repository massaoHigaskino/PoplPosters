package br.com.mm.adcertproj.poplposters.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.mm.adcertproj.poplposters.R;
import br.com.mm.adcertproj.poplposters.pref.MovieDBPreferences;

public class MovieDBHelper {
    public void showApiKeyInput(Context context, final IApiKeyInput listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getString(R.string.mdb_apikey_input_title));

        final TextView alertMessage = new TextView(context);
        alertMessage.setText(R.string.mdb_apikey_input_message);

        final EditText inputKey = new EditText(context);
        inputKey.setInputType(InputType.TYPE_CLASS_TEXT);

        final LinearLayout container = new LinearLayout(context);
        container.addView(alertMessage);
        container.addView(inputKey);

        builder.setView(container);

        builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!StringHelper.isNullOrEmpty(inputKey.getText().toString())) {
                    MovieDBPreferences.setValueApiKey(inputKey.getText().toString());
                }
                if(listener != null) {
                    listener.onApiKeyInput();
                }
            }
        });

        builder.show();
    }

    public interface IApiKeyInput {
        void onApiKeyInput();
    }
}
