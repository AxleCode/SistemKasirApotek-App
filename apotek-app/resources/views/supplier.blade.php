@extends('layout.app')

@section('content')

    <h3>Suuplier</h3>

    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">Tambah Supplier</button>
    
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">No.</th>
            <th scope="col">Nama Supplier</th>
            <th scope="col">Alamat</th>
            <th scope="col">No Telepon</th>
        </tr>
        </thead>
        <tbody>
        @foreach ($data_supplier as $supplier)
        <tr>
            <th scope="row">{{ $loop->iteration }}</th>
            <td>{{ $supplier['nama_supplier'] }}</td>
            <td>{{ $supplier['alamat'] }}</td>
             <td>{{ $supplier['no_telepon'] }}</td>
            <td>
                <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal{{ $supplier['id'] }}"><i class="bi bi-trash3"></i>Hapus</button>
                <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editModal{{ $supplier['id'] }}"><i class="bi bi-pencil-square"></i>Ubah</button>
            </td>
        </tr>    
        @endforeach
        </tbody>
    </table>

    <!-- Add Modal -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
        <form action="{{ route('supplier.store') }}" method="POST">
            @csrf
            @method('POST')
            <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Tambah Suuplier</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="nama_supplier" class="col-form-label">Nama Supplier:</label>
                        <input type="text" class="form-control" id="nama_supplier" name="nama_supplier">
                    </div>

                    <div class="form-group">
                        <label for="alamat" class="col-form-label">Alamat:</label>
                        <input type="text" class="form-control" id="alamat" name="alamat">
                    </div>

                    <div class="form-group">
                        <label for="no_telepon" class="col-form-label">No Telepon:</label>
                        <input type="number" class="form-control" id="no_telepon" name="no_telepon">
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

    @foreach ($data_supplier as $supplier)

    <!-- Edit Modal -->
    <div class="modal fade" id="editModal{{ $supplier['id'] }}" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <form action="{{ route('supplier.update', $supplier['id']) }}" method="POST">
            @csrf
            @method('PUT')
            <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Tambah Supplier</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="nama_supplier" class="col-form-label">Nama Supplier:</label>
                        <input type="text" class="form-control" id="nama_supplier" name="nama_supplier" value="{{ old('nama_supplier', $supplier['nama_supplier']) }}">
                   </div>

                   <div class="form-group">
                        <label for="nama_supplier" class="col-form-label">Alamat:</label>
                        <input type="text" class="form-control" id="alamat" name="alamat" value="{{ old('alamat', $supplier['alamat']) }}">
                   </div>

                   <div class="form-group">
                        <label for="nama_supplier" class="col-form-label">No Telepon:</label>
                        <input type="number" class="form-control" id="no_telepon" name="no_telepon" value="{{ old('no_telepon', $supplier['no_telepon']) }}">
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
    <div class="modal fade" id="deleteModal{{ $supplier['id'] }}" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Hapus Supplier</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            Apakah anda yakin menghapus data ini?
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Tutup</button>
            <form method="POST" action="{{ route('supplier.destroy', $supplier['id']) }}">
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