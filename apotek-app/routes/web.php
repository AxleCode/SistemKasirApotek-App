<?php

use App\Http\Controllers\TransactionController;
use App\Http\Controllers\ObatController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\SupplierController;
use App\Http\Controllers\UserController;
use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Http;
use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return view('login');
})->name('login');

Route::get('/dashboard', function () {
    return view('dashboard');
})->name('dashboard');

Route::resource('transaction', TransactionController::class);
Route::resource('medicine', ObatController::class);
Route::resource('category', CategoryController::class);
Route::resource('supplier', SupplierController::class);
Route::resource('user', UserController::class);

Route::post('user/search', [UserController::class, 'search'])->name('user.search');
Route::post('/register', [UserController::class, 'store'])->name('register');
Route::post('/login', [UserController::class, 'login'])->name('login');
Route::get('/logout', [UserController::class, 'logout'])->name('logout');
Route::get('register-form', [UserController::class, 'registerform'])->name('register-form');
Route::get('login-form', [UserController::class, 'loginform'])->name('login-form');


