package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

class FraisHfAdapter extends BaseAdapter {

    private final ArrayList<FraisHf> lesFrais; // liste des frais du mois
    private final LayoutInflater inflater;
    private Integer key; // pour le mois du frais hors forfait

    /**
     * Constructeur de l'adapter pour valoriser les propriétés
     *
     * @param context  Accès au contexte de l'application
     * @param lesFrais Liste des frais hors forfait
     */
    public FraisHfAdapter(Context context, ArrayList<FraisHf> lesFrais) {
        inflater = LayoutInflater.from(context);
        this.lesFrais = lesFrais;
        this.key = key;
    }

    /**
     * retourne le nombre d'éléments de la listview
     */
    @Override
    public int getCount() {
        return lesFrais.size();
    }

    /**
     * retourne l'item de la listview à un index précis
     */
    @Override
    public Object getItem(int index) {
        return lesFrais.get(index);
    }

    /**
     * retourne l'index de l'élément actuel
     */
    @Override
    public long getItemId(int index) {
        return index;
    }

    /**
     * Affichage dans la liste
     */
    @Override
    public View getView(int index, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_liste, parent, false);
            holder.txtListJour = convertView.findViewById(R.id.txtListJour);
            holder.txtListMontant = convertView.findViewById(R.id.txtListMontant);
            holder.txtListMotif = convertView.findViewById(R.id.txtListMotif);
            holder.cmdSuppHf = convertView.findViewById(R.id.cmdSuppHf);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtListJour.setText(String.format(Locale.FRANCE, "%d", lesFrais.get(index).getJour()));
        holder.txtListMontant.setText(String.format(Locale.FRANCE, "%.2f", lesFrais.get(index).getMontant()));
        holder.txtListMotif.setText(lesFrais.get(index).getMotif());
        holder.cmdSuppHf.setTag(index);
        //méthode pour la suppression du frais
        holder.cmdSuppHf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //numéro de ligne et du contexte
                int position = (int) v.getTag();
                Context context = parent.getContext();
                Log.d("contexte", "le contexte: " + context);
                Log.d("index", "l'index du frais hors forfait : " + position);
                //suppression
                lesFrais.remove(position);
                Global.listFraisMois.get(key).supprFraisHf(position);
                Serializer.serialize(Global.listFraisMois, context);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    /**
     * structure contenant les éléments d'une ligne
     */
    private class ViewHolder {
        TextView txtListJour;
        TextView txtListMontant;
        TextView txtListMotif;
        ImageButton cmdSuppHf;
    }

}
