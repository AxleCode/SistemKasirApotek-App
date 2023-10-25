@extends('layout.app')

@section('content')

    <h3 class="mb-3">User</h3>
    <form method="POST" action="{{ route('user.search') }}">
        @csrf
        <div class="input-group">
            <input type="text" class="form-control" name="nik" placeholder="Cari berdasarkan NIK...">
            <button type="submit" class="btn btn-primary">Cari</button>
        </div>
    </form>
    
    
    <table class="table table-hover">
        <thead>
          <tr>
            <th scope="col">NIK</th>
            <th scope="col">Username</th>
            <th scope="col">Role</th>
            <th scope="col">Aksi</th>
          </tr>
        </thead>
        <tbody>
        @foreach ($data_user as $user)
          <tr>
            <td>{{ $user['nik'] }}</td>
            <td>{{ $user['username'] }}</td>
            <td>{{ $user['role'] }}</td>
            <td>
                <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal{{ $user['nik'] }}"><i class="bi bi-trash3"></i>Hapus</button>
                <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editModal{{ $user['nik'] }}"><i class="bi bi-pencil-square"></i>Ubah</button>
            </td>
          </tr>    

              <!-- Delete Modal -->
    <div class="modal fade" id="deleteModal{{ $user['nik'] }}" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
      <div class="modal-dialog">
      <div class="modal-content">
          <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Hapus Transaksi</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
          Apakah anda yakin menghapus user ini?
          </div>
          <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Tutup</button>
          <form method="POST" action="{{ route('user.destroy', $user['nik']) }}">
              @method('DELETE')
              @csrf
              <button type="submit" class="btn btn-danger">Ya, saya yakin</button>
          </form>
          </div>
      </div>
      </div>
  </div>

    <!-- Edit Modal -->
  <div class="modal fade" id="editModal{{ $user['nik'] }}" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
    <form action="{{ route('user.update', $user['nik']) }}" method="POST">
      @csrf
      @method('PUT')
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Edit User</h1>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label for="nik" class="col-form-label">NIK</label>
              <input required type="text" class="form-control" id="nik" name="nik" value="{{ $user['nik'] }}" readonly>
            </div>
            <div class="form-group mt-2">
              <label for="username" class="col-form-label">Username</label>
              <input required type="text" class="form-control" id="username" name="username" value="{{ old('username', $user['username']) }}">
            </div>
            <div class="form-group mt-2">
              <label for="password" class="col-form-label">Password</label>
              <input type="password" class="form-control" id="password" name="password">
            </div>
            <div class="form-group mt-2">
              <label for="role" class="col-form-label">Role</label>
              <select class="form-control" name="role">
                <option value="ADMIN" {{ old('role', $user['role']) == 'ADMIN' ? 'selected' : '' }}>Admin</option>
                <option value="CASHIER" {{ old('role', $user['role']) == 'CASHIER' ? 'selected' : '' }}>Cashier</option>
              </select>
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
        </tbody>
    </table>


  {{-- 
  <!-- Edit Modal -->
  <div class="modal fade" id="editModal{{ $user['nik'] }}" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
    <form action="{{ route('user.update', $user['nik']) }}" method="POST">
        @csrf
        @method('PUT')
        <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Edit User</h1>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="NIK" class="col-form-label">Nomor Induk Karyawan</label>
                    <input required type="text" class="form-control" id="NIK" name="NIK" value="{{ old('NIK', $user['nik']) }}" >
                </div>
                <div class="form-group mt-2">
                    <label for="username" class="col-form-label">Username</label>
                    <input required type="text" class="form-control" id="username" name="username" value="{{ old('username', $user['username']) }}" >
                </div>
                <div class="form-group mt-2">
                  <label for="password" class="col-form-label">Password</label>
                  <input required type="text" class="form-control" id="password" name="password" value="{{ $user['password'] }}">
                </div>
                
                <div class="form-group mt-2">
                  <label for="password" class="col-form-label">Role</label>
                  <select class="form-control" name="role">
                    <option value="ADMIN" {{ old('role') == 'ADMIN' ? 'selected' : '' }}>Admin</option>
                    <option value="CASHIER" {{ old('role') == 'CASHIER' ? 'selected' : '' }}>Cashier</option>
                  </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Tutup</button>
                <button type="submit" class="btn btn-primary">Simpan</button>
            </div>
        </div>
        </div>
    </form>
  </div> --}}

@endsection