package com.example.fichadigital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TelaPrincipal extends AppCompatActivity {

    private TextView nomeUsuario, emailUsuario;
    private Button btn_deslogar, btn_escolher_perfil;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private Button btn_foto_personagem;
    private ImageView mImageFoto;
    private StorageReference storageReference;

    private Uri mSelectedUri;
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        getSupportActionBar().hide();

        IniciarComponents();

        storage = FirebaseStorage.getInstance();

        btn_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });

        btn_escolher_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this, TelaConta.class);
                startActivity(intent);
                finish();
            }
        });

        btn_foto_personagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();

                //salvarImagem();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    nomeUsuario.setText(documentSnapshot.getString("nome"));
                    emailUsuario.setText(email);
                }
            }
        });

    }

    private void IniciarComponents(){
        nomeUsuario = findViewById(R.id.textNomeUsuario);
        emailUsuario = findViewById(R.id.textEmailUsuario);
        btn_deslogar = findViewById(R.id.btn_deslogar);
        btn_escolher_perfil = findViewById(R.id.btn_escolher_perfil);
        btn_foto_personagem = findViewById(R.id.btn_selected_photo);
        mImageFoto = findViewById(R.id.imageFoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            mSelectedUri = data.getData();

            Bitmap bitmap = null;

            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mSelectedUri);
                mImageFoto.setImageDrawable(new BitmapDrawable(bitmap));
               // enviarFoto();
                btn_foto_personagem.setAlpha(0);
            }catch (IOException e){

            }

        }
    }

    private void selectPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

   /* private void upload_imagem_1(){

        StorageReference reference = storage.getReference().child("upload").child("imagens");

        StorageReference nome_imagem = reference.child("ImagemPerfil"+System.currentTimeMillis()+".jpg");

        BitmapDrawable drawable = (BitmapDrawable) mImageFoto.getDrawable();

        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        UploadTask uploadTask = nome_imagem.putBytes(bytes.toByteArray());

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Sucesso ao realizar o upload", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), "Erro ao realizar o upload", Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/

    /*private void salvarImagem(){
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);

        ref.putFile(mSelectedUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("Teste", uri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Teste", e.getMessage(), e);
                    }
                });
    }*/

   private void enviarFoto(){
       Bitmap bitmap = ((BitmapDrawable)mImageFoto.getDrawable()).getBitmap();
       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
       byte[] imagem = byteArrayOutputStream.toByteArray();

       StorageReference imgRef = storageReference.child("img-001.jpeg");
       UploadTask uploadTask = imgRef.putBytes(imagem);
       uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               Toast.makeText(getApplicationContext(), "Upload realizado com sucesso", Toast.LENGTH_SHORT).show();
           }
       });
   }
}



