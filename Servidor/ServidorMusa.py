# -*- coding: utf-8 -*-

from flask import *
from peewee import *
import json
import sys

app = Flask(__name__)

musa_db = MySQLDatabase(
    "musa", host="localhost", port=3306, user="euterpe", passwd="An6248322")

class MySQLModel(Model):
    """Database model"""
    class Meta:
        database = musa_db

class Mensaje:
    def __init__(self, error, mensaje, estado):
        self.error = error
        self.mensaje = mensaje
        self.estado = estado


class Melomano(MySQLModel):
    idMelomano = PrimaryKeyField()
    nombreMelomano = CharField()
    nombre = CharField()
    apellidos = CharField()
    password = CharField()
    fotoPerfil = BigBitField()
    correoElectronico = CharField()

class Genero(MySQLModel):
    idGenero = PrimaryKeyField()
    genero = CharField()

class Artista(MySQLModel):
    idArtista = PrimaryKeyField()
    nombre = CharField()
    biografia = CharField()
    genero = CharField()
    correoElectronico = CharField()

class Album(MySQLModel):
    idAlbum = PrimaryKeyField()
    nombre = CharField()
    portada = BlobField()
    fechaLanzamiento = DateField()
    companiaDiscografica = CharField()
    idArtista = ForeignKeyField(Artista, db_column = "idArtista")

class Cancion(MySQLModel):
    idCancion = PrimaryKeyField()
    nombre = CharField()
    idAlbum = ForeignKeyField(Album, db_column = "idAlbum")
    idGenero = ForeignKeyField(Genero, db_column = "idGenero")
    cancion = BlobField()
    duracion = IntegerField()

class Playlist(MySQLModel):
    idPlaylist = PrimaryKeyField()
    nombre = CharField()
    portada = BlobField()
    nombreUsuario = ForeignKeyField(Melomano, db_column = "idMelomano")

class CancionesPlaylist(MySQLModel):
    idPlaylist = ForeignKeyField(Playlist, db_column = "idPlaylist")
    idCancion = ForeignKeyField(Cancion, db_column = "idCancion")

class Calificacion(MySQLModel):
    idCancion = ForeignKeyField(Cancion, db_column = "idCancion")
    nombreUsuario = ForeignKeyField(Melomano, db_column = "idMelomano")
    calificacion = IntegerField()

class CancionPropia(MySQLModel):
    idCancionPropia = PrimaryKeyField()
    nombre = CharField()
    cancion = BlobField()
    nombreUsuario = ForeignKeyField(Melomano, db_column = "idMelomano")

class FotoArtista(MySQLModel):
    idFoto = PrimaryKeyField()
    foto = BlobField()
    idArtista = ForeignKeyField(Artista, db_column = "idArtista")

class Historial(MySQLModel):
    nombreUsuario = ForeignKeyField(Melomano, db_column = "idMelomano")
    idCancion = ForeignKeyField(Cancion, db_column = "idCancion")

def autenticar_melomano(melomano):
    session['logged_in'] = True
    session['username'] = melomano.nombreUsuario

@app.route("/")
def main():
    return jsonify("Musa server. versión 1.0")

@app.route("/melomano/agregar", methods=["POST"])
def registrar_melomano():
    with musa_db.atomic():
        try:
            melomano = Melomano.create(
                nombreMelomano = request.form['nombreMelomano'],
                nombre = request.form['nombre'],
                apellidos = request.form['apellidos'],
                password = request.form['password'],
                fotoPerfil = request.form['fotoPerfil'],
                correoElectronico = request.form['correoElectronico'])
            mensaje = Mensaje(False, "Usuario registrado", 200)
        except IntegrityError :
            mensaje = Mensaje(True, "El nombre de usuario ya existe", 400)
    return jsonify(error = mensaje.error, mensaje = mensaje.mensaje)

@app.route("/melomano/login", methods=["POST"])
def iniciar_sesion():
    try:
        melomano = Melomano.get(Melomano.nombreMelomano == request.form['nombreMelomano'])
        mensaje = Mensaje(False, "Sesión iniciada", 400)
    except Melomano.DoesNotExist:
        mensaje = Mensaje(True, "El nombre de usuario ya existe", 400)
    #else:   
    #    autenticar_melomano(melomano)
    
    return jsonify(error = mensaje.error, mensaje = mensaje.mensaje)

if __name__ == "__main__":
    app.run(host = '127.0.0.1', port = '5555', debug = True)
