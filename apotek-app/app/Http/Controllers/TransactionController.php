<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class TransactionController extends Controller
{

    public function generateRandomCode()
    {
        return 'CST-' . mt_rand(100000, 999999);
    }

    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
        $accessToken = session('token');
        
        $transactionResponse = Http::withHeaders([
            'Authorization' => 'Bearer ' . $accessToken,
        ])->get('http://DESKTOP-SJOEMCQ:3001/transactions/all');

        $medicineResponse = Http::withHeaders([
            'Authorization' => 'Bearer ' . $accessToken,
        ])->get('http://DESKTOP-SJOEMCQ:3002/obat/list');


        if ($transactionResponse->ok()) {
            $data_transaction = $transactionResponse->json();
            $data_medicine = $medicineResponse->json();

            $transactions = $transactionResponse->json();

            // Kelompokkan transaksi berdasarkan kode transaksi
            $groupedTransactions = [];
            foreach ($transactions as $transaction) {
                $transactionCode = $transaction['transaction_code'];
                if (!isset($groupedTransactions[$transactionCode])) {
                    $groupedTransactions[$transactionCode] = [];
                }
                $groupedTransactions[$transactionCode][] = $transaction;
            }

            return view('transaction',compact('data_transaction','data_medicine','groupedTransactions'));
        
        } else {
            return redirect()->route('login-form')
                ->with('error', 'Token tidak sesuai');
        }

    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
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

        $randomCode = $this->generateRandomCode();

        if ($medicine->ok()) {
            $data_category = $category->json();
            $data_supplier = $supplier->json();
            $data_medicine = $medicine->json();

            return view('addtransaction',compact('data_medicine', 'data_supplier', 'data_category', 'randomCode'));
        
        } else {
           return redirect()->route('login-form')
               ->with('error', 'Token tidak sesuai');
        }
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
        $transaction_code = $request->input('transaction_code');
        $medicines_id = $request->input('medicine_id');
        $quantities = $request->input('quantity');

        $accessToken = session('token');
        $hasPositiveQuantity = false;

        foreach ($medicines_id as $index => $medicine_id) {
            $quantity = $quantities[$index];
            
            if ($quantity > 0) {
                $hasPositiveQuantity = true;
                $response = Http::withHeaders([
                    'Authorization' => 'Bearer ' . $accessToken,
                ])->post('http://DESKTOP-SJOEMCQ:3001/transactions/add', [
                    'transaction_code' => $transaction_code,
                    'medicine_id' => $medicine_id,
                    'quantity' => $quantity,
                ]);
            }
        }
        if ($hasPositiveQuantity) {
            if ($response->successful()) {
                return redirect()->route('transaction.index')
                    ->with('success', 'Berhasil Ditambahkan');
            } else {
                return redirect()->route('transaction.index')
                    ->with('error', 'Stok tabung tidak mencukupi');
            }
        } else {
            // Tidak ada quantity yang lebih besar dari 0
            return redirect()->route('transaction.index')
                ->with('error', 'Jumlah obat harus lebih dari 0');
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
        //
        $transaction_code = $request->input('transaction_code');
        $medicine_id = $request->input('medicine_id');
        $quantity = $request->input('quantity');

        $accessToken = session('token');

        $response = Http::withHeaders([
            'Authorization' => 'Bearer ' . $accessToken,
        ])->put('http://DESKTOP-SJOEMCQ:3001/transactions/edit/'. $id, [
            'transaction_code' => $transaction_code,
            'medicine_id' => $medicine_id,
            'quantity' => $quantity,
        ]);

        if ($response->successful()) {
            return redirect()->route('transaction.index')
                    ->with('success', 'Berhasil Ditambahkan');
        } else {
            return redirect()->route('transaction.index')
                    ->with('error', 'Stok tabung tidak mencukupi');
        }
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy($id)
    {
        //
        $accessToken = session('token');

        $response = Http::withHeaders([
            'Authorization' => 'Bearer ' . $accessToken,
        ])->delete('http://DESKTOP-SJOEMCQ:3001/transactions/delete/' . $id);


        if ($response->successful()) {
            return redirect()->route('transaction.index')
                    ->with('success', 'Berhasil Ditambahkan');
        } else {
            return redirect()->route('transaction.index')
                    ->with('error', 'Stok tabung tidak mencukupi');
        }
    }
}
