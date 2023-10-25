@extends('layout.app')

@section('content')

    <h3>Obat</h3>

    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">Tambah Obat</button>
    
    <table class="table table-hover">
        <thead>
          <tr>
            <th scope="col">No.</th>
            <th scope="col">Nama Obat</th>
            <th scope="col">Kategori</th>
            <th scope="col">Supplier</th>
            <th scope="col">Stok</th>
            <th scope="col">Harga</th>
            <th scope="col">Aksi</th>
          </tr>
        </thead>
        <tbody>
        @foreach ($data_medicine as $obat)
          <tr>
            <th scope="row">{{ $loop->iteration }}</th>
            <td>{{ $obat['nama_obat'] }}</td>
            <td>{{ $obat['obatDataKategori'][0]['nama_kategori'] }}</td>
            <td>{{ $obat['obatDataSupplier'][0]['nama_supplier'] }}</td>
            <td>{{ $obat['stok'] }}</td>
            <td>{{ $obat['harga'] }}</td>
            <td>
                <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal{{ $obat['id'] }}"><i class="bi bi-trash3"></i>Hapus</button>
                <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editModal{{ $obat['id'] }}"><i class="bi bi-pencil-square"></i>Ubah</button>
            </td>
          </tr>    
        @endforeach
        </tbody>
    </table>

    <!-- Add Modal -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
        <form action="{{ route('medicine.store') }}" method="POST">
            @csrf

            @method('POST')
            <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Tambah Obat</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                        <div class="form-group">
                            <label for="nama_obat" class="col-form-label">Nama Obat:</label>
                            <input type="text" class="form-control" id="nama_obat" name="nama_obat">
                        </div>
                        <div class="form-group mt-2">
                            <label for="id_supplier" class="col-form-label">Supplier:</label>
                            <select class="form-select" aria-label="Default select example" id="id_supplier" name="id_supplier">
                                <option selected>Pilih supplier</option>
                                @foreach ($data_supplier as $supplier)
                                    <option value="{{ $supplier['id'] }}">{{ $supplier['nama_supplier'] }}</option>
                                @endforeach
                            </select>
                        </div>
                        <div class="form-group mt-2">
                            <label for="id_kategori" class="col-form-label">Kategori:</label>
                            <select class="form-select" aria-label="Default select example" id="id_kategori" name="id_kategori">
                                <option selected>Pilih kategori obat</option>
                                @foreach ($data_category as $category)
                                    <option value="{{ $category['id'] }}">{{ $category['nama_kategori'] }}</option>
                                @endforeach
                            </select>
                        </div>
                        <div class="form-group mt-2">
                            <label for="stok" class="col-form-label">Stok:</label>
                            <input type="number" class="form-control" id="stok" name="stok">
                        </div>
                        <div class="form-group mt-2">
                            <label for="harga" class="col-form-label">Harga:</label>
                            <input type="number" class="form-control" id="harga" name="harga">
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

    <!-- Edit Modal -->
    @foreach($data_medicine as $obat)
    <div class="modal fade" id="editModal{{ $obat['id'] }}" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <form action="{{ route('medicine.update', $obat['id']) }}" method="POST">
            @csrf

            @method('PUT')
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Ubah Data Obat</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="nama_obat" class="col-form-label">Nama Obat:</label>
                            <input type="text" class="form-control" id="nama_obat" name="nama_obat" value="{{ old('nama_obat', $obat['nama_obat']) }}">
                        </div>
                        <div class="form-group mt-2">
                            <label for="id_kategori" class="col-form-label">Kategori:</label>
                            <select class="form-select" aria-label="Default select example" id="id_kategori" name="id_kategori">
                                @foreach ($data_category as $category)
                                    <option value="{{ $category['id'] }}" @if ($category['id'] === $obat['id']) selected @endif>
                                        {{ $category['nama_kategori'] }}
                                    </option>
                                @endforeach
                            </select>
                        </div>
                        <div class="form-group mt-2">
                            <label for="id_supplier" class="col-form-label">Supplier:</label>
                            <select class="form-select" aria-label="Default select example" id="id_supplier" name="id_supplier">
                                @foreach ($data_supplier as $supplier)
                                    <option value="{{ $supplier['id'] }}" @if($supplier['id'] === $obat['id']) selected @endif>
                                    {{ $supplier['nama_supplier'] }}</option>
                                @endforeach
                            </select>
                        </div>
                        <div class="form-group mt-2">
                            <label for="stok" class="col-form-label">Stok:</label>
                            <input type="number" class="form-control" id="stok" name="stok" value="{{ old('stok', $obat['stok']) }}">
                        </div>
                        <div class="form-group mt-2">
                            <label for="harga" class="col-form-label">Harga:</label>
                            <input type="number" class="form-control" id="harga" name="harga" value="{{ old('harga', $obat['harga']) }}">
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
    @endforeach

    <!-- Delete Modal -->
    @foreach($data_medicine as $obat)
    <div class="modal fade" id="deleteModal{{ $obat['id'] }}" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Hapus Obat</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            Apakah anda yakin menghapus data ini?
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Tutup</button>
            <form action="{{ route('medicine.destroy', $obat['id']) }}" method="POST">
                @csrf

                @method('DELETE')
                <button type="submit" class="btn btn-danger">Ya, saya yakin</button>
            </form>
        </div>
        </div>
        </div>
    </div>
    @endforeach
    
@endsection