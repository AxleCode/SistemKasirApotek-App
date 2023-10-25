<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class SupplierController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
         //
         $accessToken = session('token');

         $supplier = Http::withHeaders([
             'Authorization' => 'Bearer ' . $accessToken,
         ])->get('http://DESKTOP-SJOEMCQ:3004/supplier/all');
 
         if ($supplier->ok()) {
             $data_supplier = $supplier->json();
 
             return view('supplier',compact('data_supplier'));
         
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
        $nama_supplier = $request->input('nama_supplier');
        $alamat = $request->input('alamat');
        $no_telepon = $request->input('no_telepon');

        $accessToken = session('token');

        $response = Http::withHeaders([
            'Authorization' => 'Bearer ' . $accessToken,
        ])->post('http://DESKTOP-SJOEMCQ:3004/supplier/add', [
            'nama_supplier' => $nama_supplier,
            'alamat' => $alamat,
            'no_telepon' => $no_telepon,
        ]);

        if($response->successful()) {
            return redirect()->route('supplier.index')
            ->with('success', 'Berhasil tambah data');
        } else {
            return redirect()->route('supplier.index')
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
        $nama_supplier = $request->input('nama_supplier');
        $alamat = $request->input('alamat');
        $no_telepon = $request->input('no_telepon');

        $accessToken = session('token');

        $response = Http::withHeaders([
            'Authorization' => 'Bearer ' . $accessToken,
        ])->put('http://DESKTOP-SJOEMCQ:3004/supplier/update/' . $id, [
            'nama_supplier' => $nama_supplier,
            'alamat' => $alamat,
            'no_telepon' => $no_telepon,
        ]);

        if($response->successful()) {
            return redirect()->route('supplier.index')
            ->with('success', 'Berhasil update data!');
        } else {
            return redirect()->route('supplier.index')
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
        ])->delete('http://DESKTOP-SJOEMCQ:3004/supplier/delete/' . $id);

        if($response->successful()) {
            return redirect()->route('supplier.index')
            ->with('success', 'Berhasil hapus data!');
        } else {
            return redirect()->route('supplier.index')
            ->with('error', 'Gagal hapus data');
        }
    }
}