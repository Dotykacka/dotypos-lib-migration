package com.dotypos.lib.migration.demo

data class Configuration(
    val cloudId: String,
    val branchId: String,
    val name: String?,
    val email: String?,
    val phone: String?,
    val licenseKey: String?,
)

fun createConfiguration(arguments: Array<String>) = Configuration(
    cloudId = arguments.requireArgOrEnv("cid", "DEMO_CLOUD_ID"),
    branchId = arguments.requireArgOrEnv("bid", "DEMO_BRANCH_ID"),
    email = arguments.getArgOrEnv("email", "DEMO_EMAIL") ?: "john.doe@dotypos.com",
    name = arguments.getArgOrEnv("name", "DEMO_NAME") ?: "John Doe",
    phone = arguments.getArgOrEnv("phone", "DEMO_PHONE") ?: "+420 111 222 333",
    licenseKey = arguments.getArgOrEnv("license", "DEMO_LICENSE_KEY") ?: "EXAMPLE",
)

private fun Array<String>.requireArgOrEnv(argName: String, envName: String): String {
    return getArgOrEnv(argName, envName)
        ?: throw throw IllegalStateException("Missing required argument '$argName' or environment variable '$envName'")
}

private fun Array<String>.getArgOrEnv(argName: String, envName: String): String? {
    return getArg(argName)
        ?: System.getenv(envName)
}

private fun Array<String>.getArg(argName: String): String? {
    return find { it.startsWith("$argName=") }
        ?.substring("$argName=".length)
}

private fun requireEnv(envName: String) =
    System.getenv(envName) ?: throw IllegalStateException("Missing required environment variable '$envName'")

private fun Array<String>.requireArg(argName: String) =
    find { it.startsWith("$argName=") }
        ?.substring("$argName=".length)