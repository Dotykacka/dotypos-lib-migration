# Demo migration files tool

## Usage
`./gradlew run --args='cid=1111 bid==3333'`

Where `cid` is Cloud ID, and `bid` is Branch ID. Those values are used only for Cloud data creation.

### Optional arguments

| name      | Description               | Default Value         |
|:----------|:--------------------------|:----------------------|
| email     | Email of Administrator    | john.doe@dotypos.com  |
| name      | Name of Administrator     | John Doe              |
| phone     | Phone of Administrator    | +420 111 222 333      |
| license   | License key               | EXAMPLE               |

## Output files
All migration files are created in the output directory. ZIP file is also created for convenience.

