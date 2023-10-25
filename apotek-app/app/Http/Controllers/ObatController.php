<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class ObatController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
         //
         $accessToken = session('token');
        
         $category = Http::withHeaders([
             'Authorization' => 'Bearer ' . $accessToken,
         ])->get('http://DESKTOP-SJOEMCQ:3003/kategori/all');

         $supplier = Http::withHeaders([
             'Authorization' => 'Bearer ' . $accessToken,
         ])->get('http://DESKTOP-SJOEMCQ:3004/supplier/all');
 
         $medicine = Http::withHeaders([
             'Authorization' => 'Bearer ' . $accessToken,
         ])->get('http://DESKTOP-SJOEMCQ:3002/obat/list');
 
 
         if ($medicine->ok()) {
             $data_category = $category->json();
             $data_supplier = $supplier->json();
             $data_medicine = $medicine->json();
 
             return view('medicine',compact('data_medicine', 'data_supplier', 'data_category'));
         
         } else {
            return redirect()->route('login-form')
                ->with('error', 'Token tidak sesuai');
        }

        // return view('medicine');
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $id_kategori = $request->input('id_kategori');
        $id_supplier = $request->input('id_supplier');
        $nama_obat = $request->input('nama_obat');
        $stok = $request->input('stok');
        $harga = $request->input('harga');

        $accessToken = session('token');

        $response = Http::withHeaders([
            'Authorization' => 'Bearer ' . $accessToken,
        ])->post('http://DESKTOP-SJOEMCQ:3002/obat/add', [
            'id_kategori' => $id_kategori,
            'id_supplier' => $id_supplier,
            'nama_obat' => $nama_obat,
            'stok' => $stok,
            'harga' => $harga,
        ]);

        if($response->successful()) {
            return redirect()->route('medicine.index')
            ->with('success', 'Berhasil tambah data');
        } else {
            return redirect()->route('medicine.index')
            ->with('error', 'Gagal tambah data');
        }

    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(string $id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, $id)
    {
        $id_kategori = $request->input('id_kategori');
        $id_supplier = $request->input('id_supplier');
        $nama_obat = $request->input('nama_obat');
        $stok = $request->input('stok');
        $harga = $request->input('harga');

        $accessToken = session('token');

        $response = Http::withHeaders([
            'Authorization' => 'Bearer ' . $accessToken,
        ])->put('http://DESKTOP-SJOEMCQ:3002/obat/update/' . $id, [
            'id_kategori' => $id_kategori,
            'id_supplier' => $id_supplier,
            'nama_obat' => $nama_obat,
            'stok' => $stok,
            'harga' => $harga,
        ]);

        if($response->successful()) {
            return redirect()->route('medicine.index')
            ->with('success', 'Berhasil update data!');
        } else {
            return redirect()->route('medicine.index')
            ->with('error', 'Gagal update data');
        }
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy($id)
    {
        $accessToken = session('token');

        $response = Http::withHeaders([
            'Authorization' => 'Bearer ' . $accessToken,
        ])->delete('http://DESKTOP-SJOEMCQ:3002/obat/delete/' . $id);

        if($response->successful()) {
            return redirect()->route('medicine.index')
            ->with('success', 'Berhasil hapus data!');
        } else {
            return redirect()->route('medicine.index')
            ->with('error', 'Gagal hapus data');
        }
    }
}