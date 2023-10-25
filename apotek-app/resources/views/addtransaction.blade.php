@extends('layout.app')

@section('content')

    <h3>Obat</h3>
    <form action="{{ route('transaction.store') }}" method="POST">
        @csrf
        @method('POST')
        <button type="submit" class="btn btn-primary">Simpan Transaksi</button>
        <div class="form-group">
            <label for="transaction_code" class="col-form-label">Kode Transaksi</label>
            <input required type="text" class="form-control" id="transaction_code" name="transaction_code" value="{{ $randomCode }}" readonly>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">No.</th>
                <th scope="col">Nama Obat</th>
                <th scope="col">Kategori</th>
                <th scope="col">Supplier</th>
                <th scope="col">Stok</th>
                <th scope="col">Harga</th>
                <th scope="col">Jumlah</th>
            </tr>
            </thead>
            <tbody>
            @foreach ($data_medicine as $obat)
            <tr>
                <th scope="row">{{ $loop->iteration }}
                    <input hidden required type="number" class="form-control" id="medicine_id" name="medicine_id[]" value="{{ $obat['id'] }}">
                </th>
                <td>{{ $obat['nama_obat'] }}</td>
                <td>{{ $obat['obatDataKategori'][0]['nama_kategori'] }}</td>
                <td>{{ $obat['obatDataSupplier'][0]['nama_supplier'] }}</td>
                <td>{{ $obat['stok'] }}</td>
                <td>{{ $obat['harga'] }}</td>
                <td>
                    <input required type="number" class="form-control" id="quantity" name="quantity[]" value="0">
                </td>
            </tr>    
            @endforeach
            </tbody>
        </table>
    </form>
    
@endsection