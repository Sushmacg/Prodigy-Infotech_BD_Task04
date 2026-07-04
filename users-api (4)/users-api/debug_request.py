import http.client
import json

HOST = 'localhost'
PORT = 8020


def request(method, path, body=None, headers=None):
    conn = http.client.HTTPConnection(HOST, PORT, timeout=10)
    hdrs = {} if headers is None else dict(headers)
    body_bytes = None
    if body is not None:
        if isinstance(body, str):
            body_bytes = body.encode('utf-8')
        else:
            body_bytes = json.dumps(body, separators=(',', ':')).encode('utf-8')
        hdrs['Content-Type'] = 'application/json'
        hdrs['Content-Length'] = str(len(body_bytes))
    conn.request(method, path, body_bytes, hdrs)
    res = conn.getresponse()
    raw = res.read()
    text = raw.decode('utf-8')
    try:
        data = json.loads(text)
    except Exception:
        data = text
    print('REQ', method, path, headers if headers else hdrs, 'len', len(body_bytes) if body_bytes else 0)
    print('BODY_BYTES', body_bytes)
    print('RESP', res.status, text)
    return res.status, data

status, data = request('POST', '/api/auth/login', {'email': 'admin@example.com', 'password': 'ChangeThisPassword123'})
if status != 200:
    raise SystemExit('login failed')
token = data['token']

status, data = request('POST', '/api/users', {'name': 'TempUser3', 'email': 'tempuser3@example.com', 'password': 'password123', 'age': 30, 'role': 'USER'}, headers={'Authorization': 'Bearer ' + token})
if status != 201:
    raise SystemExit('create failed')
userid = data['id']

status, data = request('PUT', f'/api/users/{userid}', {'name': 'X', 'role': 'ADMIN'}, headers={'Authorization': 'Bearer ' + token})
print('FINAL', status, data)
