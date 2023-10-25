@extends('layout.app')

@section('content')

    <h3>Transaksi</h3>
    
    <table class="table table-hover">
        <thead>
          <tr>
            <th scope="col">No.</th>
            <th scope="col">Kode Transaksi</th>
            <th scope="col">Tanggal</th>
            <th scope="col">Total</th>
            <th scope="col">Aksi</th>
          </tr>
        </thead>
        <tbody>
            @foreach ($groupedTransactions as $transactionCode => $transactions)
                @php
                    $totalPrice = 0;
                    foreach ($transactions as $transaction) {
                        $totalPrice += $transaction['quantity'] * $transaction['obatDataList'][0]['harga'];
                    }
                @endphp
                <tr>
                    <th scope="row">{{ $loop->iteration }}</th>
                    <td>{{ $transactionCode }}</td>
                    <td>{{ date('Y-m-d', strtotime($transactions[0]['createdAt'])) }}</td>
                    <td>Rp{{ number_format($totalPrice, 0, ',', '.') }}</td>
                    <td>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#transactionDetailModal{{ $transactionCode }}">Detail</button>
                    </td>
                </tr>
            @endforeach
        </tbody>
    </table>

    @foreach ($groupedTransactions as $transactionCode => $transactions)
        <div class="modal fade" id="transactionDetailModal{{ $transactionCode }}" tabindex="-1" aria-labelledby="transactionDetailModalLabel{{ $transactionCode }}" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="transactionDetailModalLabel{{ $transactionCode }}">Detail Transaksi {{ $transactionCode }}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Nama Obat</th>
                                    <th>Jumlah</th>
                                    <th>Harga</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                @foreach ($transactions as $transaction)
                                    <tr>
                                        <td>{{ $transaction['obatDataList'][0]['nama_obat'] }}</td>
                                        <td>{{ $transaction['quantity'] }}</td>
                                        <td>Rp{{ number_format($transaction['obatDataList'][0]['harga'], 0, ',', '.') }}</td>
                                        <td>Rp{{ number_format($transaction['quantity'] * $transaction['obatDataList'][0]['harga'], 0, ',', '.') }}</td>
                                        <td>
                                            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal{{ $transaction['id'] }}"><i class="bi bi-trash3"></i>Hapus</button>
                                            <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editModal{{ $transaction['id'] }}"><i class="bi bi-pencil-square"></i>Ubah</button>
                                        </td>
                                    </tr>
                                @endforeach
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    @endforeach

    @foreach ($data_transaction as $transaction)

    <!-- Edit Modal -->
    <div class="modal fade" id="editModal{{ $transaction['id'] }}" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <form action="{{ route('transaction.update', $transaction['id']) }}" method="POST">
            @csrf
            @method('PUT')
            <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Tambah Transaksi</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="transaction_code" class="col-form-label">Kode Transaksi</label>
                        <input required type="text" class="form-control" id="transaction_code" name="transaction_code" value="{{ old('transaction_code', $transaction['transaction_code']) }}" readonly>
                    </div>
                    <div class="form-group mt-2">
                        <label for="medicine_id" class="col-form-label">Nama Obat</label>
                        <select required class="form-select" id="medicine_id" name="medicine_id" aria-label="Default select example">
                            @foreach ($data_medicine as $medicine)
                                <option value="{{ $medicine['id'] }}" @if ($medicine['id'] === $transaction['medicine_id']) selected @endif>
                                    {{ $medicine['nama_obat'] }}
                                </option>
                            @endforeach
                        </select>
                    </div>
                    <div class="form-group mt-2">
                        <label for="quantity" class="col-form-label">Jumlah</label>
                        <input required type="number" class="form-control" id="quantity" name="quantity" value="{{ old('quantity', $transaction['quantity']) }}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Tutup</button>
                    <button type="submit" class="btn btn-primary">Simpan</button>
                </div>
            </div>
            </div>
        </form>
    </div>

    <!-- Delete Modal -->
    <div class="modal fade" id="deleteModal{{ $transaction['id'] }}" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Hapus Transaksi</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            Apakah anda yakin menghapus data ini?
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Tutup</button>
            <form method="POST" action="{{ route('transaction.destroy', $transaction['id']) }}">
                @method('DELETE')
                @csrf
                <button type="submit" class="btn btn-danger">Ya, saya yakin</button>
            </form>
            </div>
        </div>
        </div>
    </div>

    @endforeach
@endsection