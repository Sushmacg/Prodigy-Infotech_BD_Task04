import http.client
import json

HOST = 'localhost'
PORT = 8023


def request(method, path, body=None, headers=None):
    conn = http.client.HTTPConnection(HOST, PORT, timeout=10)
    hdrs = {} if headers is None else dict(headers)
    payload = None
    if body is not None:
        if isinstance(body, str):
            payload = body.encode('utf-8')
        else:
            payload = json.dumps(body).encode('utf-8')
        hdrs['Content-Type'] = 'application/json'
        hdrs['Content-Length'] = str(len(payload))
    conn.request(method, path, payload, hdrs)
    res = conn.getresponse()
    text = res.read().decode('utf-8')
    try:
        data = json.loads(text)
    except Exception:
        data = text
    print('---')
    print('REQ', method, path)
    print('HEADERS', hdrs)
    print('BODY_BYTES', payload)
    print('RESP', res.status, data)
    return res.status, data

status, data = request('POST', '/api/auth/login', {'email': 'admin@example.com', 'password': 'ChangeThisPassword123'})
print('LOGIN', status, data)
if status != 200:
    raise SystemExit('Login failed')

token = data['token']
status, data = request('POST', '/api/users', {'name': 'TempUserD', 'email': 'tempuserd_' + token[:8] + '@example.com', 'password': 'password123', 'age': 30, 'role': 'USER'}, headers={'Authorization': 'Bearer ' + token})
print('CREATE', status, data)
if status != 201:
    raise SystemExit('Create failed')
userid = data['id']
print('USER ID', userid)
for payload in [ {'name': 'X'}, {'role': 'ADMIN'}, {'name': 'X', 'role': 'ADMIN'} ]:
    status, data = request('PUT', f'/api/users/{userid}', payload, headers={'Authorization': 'Bearer ' + token})
    print('UPDATE RESULT', payload, status, data)
