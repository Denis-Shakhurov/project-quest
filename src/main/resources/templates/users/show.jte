@import dto.UserPage
@param UserPage page
@template.layout.page(
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>${page.getUser().getName()}</h1>
        </div>
    </section>
`
)