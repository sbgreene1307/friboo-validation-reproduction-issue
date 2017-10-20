# exception-handling-reproduction

A project based on friboo-ext-zalando framework.

## Development

You can use `make.sh` to start the a Docker container with PostgreSQL database:

```
$ ./make.sh db
```

Run  the application:

```
$ lein repl
user=> (reset)
```

For REPL-driven interactive development configuration variables can be provided in `dev-config.edn` file, which will be read on each system restart.

## Testing

```
$ lein test
```

## Building

```
$ lein do uberjar, scm-source
$ docker build -t exception-handling-reproduction .
```

## Running

```
$ docker run -it --rm -p 8080:8080 exception-handling-reproduction
```

The following configuration environment variables are available:

| Variable | Meaning | Default | Example |
|---|---|---|---|
| TOKENINFO_URL | Token info URL to validate access tokens against | Empty, by default security is not enforced | `https://auth.example.com/oauth2/tokeninfo` |
| DB_SUBNAME | PostgreSQL connection string | `//localhost:5432/exception-handling-reproduction` | `//exception-handling-reproduction.db.example.com:5432/exception-handling-reproduction?ssl=true` |
| DB_USER | PostgreSQL username | `postgres` | `mjackson` |
| DB_PASSWORD | PostgreSQL password | `postgres` | `billiejeanisnotmylover` |
| API_EXAMPLE_PARAM | Example parameter with `:api-` prefix | `bar` | `foo` |

## License

Copyright © 2016 Zalando SE

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
