from flask import Flask
from flask import request
from flask import jsonify

app = Flask(__name__)

@app.route("/")
def main():
    return jsonify("Musa server. versión 1.0")

@app.route("/sumar", methods=["POST"])
def sumar():
   num1 = request.form['num1']
   num2 = request.form['num2']
   return jsonify(int(num1) + int(num2))

if __name__ == "__main__":
    app.run(host = '192.168.0.16', port ='5555' )
