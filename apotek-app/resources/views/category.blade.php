@extends('layout.app')

@section('content')

    <h3>Kategori</h3>

    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">Tambah Kategori</button>
    
    <table class="table table-hover">
        <thead>
          <tr>
            <th scope="col">No.</th>
            <th scope="col">Nama Kategori</th>
            <th scope="col">Deskripsi</th>
            <th scope="col">Aksi</th>
          </tr>
        </thead>
        <tbody>
        @foreach ($data_category as $category)
          <tr>
            <th scope="row">{{ $loop->iteration }}</th>
            <td>{{ $category['nama_kategori'] }}</td>
            <td>{{ $category['deskripsi'] }}</td>
            <td>
                <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal{{ $category['id'] }}"><i class="bi bi-trash3"></i>Hapus</button>
                <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editModal{{ $category['id'] }}"><i class="bi bi-pencil-square"></i>Ubah</button>
            </td>
          </tr>    
        @endforeach
        </tbody>
    </table>

    <!-- Add Modal -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
        <form action="{{ route('category.store') }}" method="POST">
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
                            <label for="nama_kategori" class="col-form-label">Nama Kategori:</label>
                            <input type="text" class="form-control" id="nama_kategori" name="nama_kategori">
                        </div>
                        <div class="form-group">
                            <label for="deskripsi" class="col-form-label">Deskripsi:</label>
                            <input type="text" class="form-control" id="deskripsi" name="deskripsi">
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
    @foreach($data_category as $category)
    <div class="modal fade" id="editModal{{ $category['id'] }}" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <form action="{{ route('category.update', $category['id']) }}" method="POST">
            @csrf

            @method('PUT')
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Ubah Data Kategori</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="nama_kategori" class="col-form-label">Nama Kategori:</label>
                            <input type="text" class="form-control" id="nama_kategori" name="nama_kategori" value="{{ old('nama_kategori', $category['nama_kategori']) }}">
                        </div>
                        <div class="form-group">
                            <label for="deskripsi" class="col-form-label">Deskripsi:</label>
                            <input type="text" class="form-control" id="deskripsi" name="deskripsi" value="{{ old('deskripsi', $category['deskripsi']) }}">
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
    @foreach($data_category as $category)
    <div class="modal fade" id="deleteModal{{ $category['id'] }}" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Hapus Kategori</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
            Apakah anda yakin menghapus data ini?
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Tutup</button>
            <form action="{{ route('category.destroy', $category['id']) }}" method="POST">
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