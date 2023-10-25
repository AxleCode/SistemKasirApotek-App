@extends('layout.app')

@section('content')

<h3 class="mb-3">Search Results</h3>
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
        <tr>
            <td>{{ $user['nik'] }}</td>
            <td>{{ $user['username'] }}</td>
            <td>{{ $user['role'] }}</td>
            <td>
                <a href="/user" class="btn btn-warning">Kembali</a>
            </td>
        </tr>
    </tbody>
</table>



@endsection
