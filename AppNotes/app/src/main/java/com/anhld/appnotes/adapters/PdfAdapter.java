package com.anhld.appnotes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anhld.appnotes.R;
import com.anhld.appnotes.listeners.OnPdfFileSelectListener;

import java.io.File;
import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfViewHolder> {
    private final Context context;
    private final List<File> pdfFiles;
    private final OnPdfFileSelectListener listener;

    public PdfAdapter(Context context, List<File> pdfFiles, OnPdfFileSelectListener listener) {
        this.context = context;
        this.pdfFiles = pdfFiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PdfViewHolder(LayoutInflater.from(context).inflate(R.layout.element_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        holder.tvName.setText(pdfFiles.get(position).getName());
        holder.tvName.setSelected(true);

        holder.container.setOnClickListener(view -> listener.onPdfSelected(pdfFiles.get(position)));
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }
}
